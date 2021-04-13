package com.pd.finance.service;

import com.pd.finance.config.ApplicationConfig;
import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.infra.IObjectConverter;
import com.pd.finance.model.*;
import com.pd.finance.persistence.StockExchangeDetailsRepository;
import com.pd.finance.request.CreateStockExchangeDetailsRequest;
import com.pd.finance.response.EquityStockExchangeDetailsResponse;
import com.pd.finance.response.chart.ChartResponse;
import com.pd.finance.response.summary.EquitySummaryResponse;
import com.pd.finance.utils.CommonUtils;
import com.pd.finance.utils.JsonUtils;
import okhttp3.Interceptor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.Period;
import java.time.temporal.TemporalAmount;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class YahooService extends AbstractHttpService implements IYahooService {
    private static final Logger logger = LoggerFactory.getLogger(YahooService.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private ApplicationConfig config;
    @Autowired
    private StockExchangeDetailsRepository stockExchangeDetailsRepository;
    @Autowired
    private IObjectConverter objectConverter;
    @Autowired
    private IEquityService equityService;
    @Autowired
    private IDocumentService documentService;

    @Resource(name = "rateLimitInterceptor")
    private Interceptor rateLimitInterceptor;

    public YahooService() {

    }

    @Override
    public EquityHistoricalIntervalData getHistoricalStockPrice(EquityIdentifier identifier, TemporalAmount amountToSubtract, HistoricalDataInterval interval) throws ServiceException {
        logger.info("getHistoricalStockPrice exec started for equity {}", identifier);
        EquityHistoricalIntervalData historicalData = null;
        try {
            Equity equity = equityService.getEquity(identifier);
            EquityStockExchangeDetailsResponse stockExchangeDetails = equity.getStockExchangeDetails();
            if (stockExchangeDetails != null) {

                EquityHistoricalData existinghistoricalData = equity.getHistoricalData();

                String historicalDataString = getHistoricalPriceCsvString(amountToSubtract, stockExchangeDetails.getSymbol(), existinghistoricalData, interval);
                if (StringUtils.isNotBlank(historicalDataString)) {

                    Set<EquityHistoricalDataLineItem> lineItemsCollector = new HashSet<>();

                    collectExistingLineItems(existinghistoricalData, lineItemsCollector,interval);

                    collectNewLineItems(historicalDataString, lineItemsCollector);

                    if (CollectionUtils.isNotEmpty(lineItemsCollector)) {
                        historicalData = getHistoricalData(lineItemsCollector, interval);
                    }
                }
            }
            return historicalData;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @NotNull
    private EquityHistoricalIntervalData getHistoricalData(Set<EquityHistoricalDataLineItem> itemsSet, HistoricalDataInterval interval) {
        EquityHistoricalIntervalData historicalData;
        List<EquityHistoricalDataLineItem> lineItems = new ArrayList<>(itemsSet);
        Collections.sort(lineItems);
        EquityHistoricalDataLineItem first = lineItems.get(0);
        EquityHistoricalDataLineItem last = lineItems.get(lineItems.size() - 1);
        historicalData = new EquityHistoricalIntervalData(interval, first.getDate(), last.getDate());
        historicalData.addLineItems(lineItems);
        return historicalData;
    }

    private void collectExistingLineItems(EquityHistoricalData existinghistoricalData, Set<EquityHistoricalDataLineItem> lineItemsCollector, HistoricalDataInterval interval) {

        if (existinghistoricalData != null) {
            EquityHistoricalIntervalData intervalData = existinghistoricalData.getIntervalData(interval);
            if(intervalData!=null){
                lineItemsCollector.addAll(intervalData.getLineItems());
            }

        }
    }

    private void collectNewLineItems(String historicalDataString, Set<EquityHistoricalDataLineItem> lineItemsCollector) throws IOException {
        try (Reader reader = new StringReader(historicalDataString);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
            for (CSVRecord record : csvParser) {

                EquityHistoricalDataLineItem equityHistoricalDataLineItem = getEquityHistoricalDataLineItem(record);
                lineItemsCollector.add(equityHistoricalDataLineItem);

            }
        }
    }

    @NotNull
    private EquityHistoricalDataLineItem getEquityHistoricalDataLineItem(CSVRecord record) {
        return new EquityHistoricalDataLineItem(
                CommonUtils.extractDateFromText(record.get("Date"), dateFormat),
                CommonUtils.extractDecimalFromText(record.get("Open")),
                CommonUtils.extractDecimalFromText(record.get("Close")),
                CommonUtils.extractDecimalFromText(record.get("Low")),
                CommonUtils.extractDecimalFromText(record.get("High")),
                CommonUtils.extractDecimalFromText(record.get("Adj Close")),
                CommonUtils.extractIntegerFromText(record.get("Volume"))
        );
    }

    private String getHistoricalPriceCsvString(TemporalAmount amountToSubtract, String equitySymbol, EquityHistoricalData existinghistoricalData, HistoricalDataInterval interval) throws ServiceException {

        Instant startTime = getStartTime(amountToSubtract, existinghistoricalData, interval);
        Instant endTime = Instant.now();

        long period1 = CommonUtils.atStartOfDay(startTime).getEpochSecond();
        long period2 =  CommonUtils.atStartOfDay(endTime).getEpochSecond();

        String historicalPriceCsvString = getHistoricalPriceCsvString(equitySymbol, period1, period2, interval);
        if (historicalPriceCsvString == null) {
            logger.info("Historical data no available from start time {},getting max available", startTime);
            period1 = 0L;
            historicalPriceCsvString = getHistoricalPriceCsvString(equitySymbol, period1, period2, interval);
        }
        return historicalPriceCsvString;
    }

    private Instant getStartTime(TemporalAmount amountToSubtract, EquityHistoricalData existinghistoricalData, HistoricalDataInterval interval) {
        Instant startTime = null;

        if (existinghistoricalData != null) {
            EquityHistoricalIntervalData intervalData = existinghistoricalData.getIntervalData(interval);
            if (intervalData != null) {
                List<EquityHistoricalDataLineItem> lineItems = intervalData.getLineItems();
                Collections.sort(lineItems);
                long endTime = lineItems.get(lineItems.size() - 1).getDate().getTime();
                startTime = Instant.ofEpochMilli(endTime);
            } else {
                startTime = Instant.now().minus(amountToSubtract);
            }

        } else {
            startTime = Instant.now().minus(amountToSubtract);
        }
        return startTime;
    }

    private String getHistoricalPriceCsvString(String equitySymbol, long period1, long period2, HistoricalDataInterval interval) throws ServiceException {
        String csvString = null;
        String historicalDataUrl = MessageFormat.format(config.getEnvProperty("HistoricalDataUrl"), equitySymbol, String.valueOf(period1), String.valueOf(period2), interval.getIntervalString(), true);
        try {

            WebDocument webDocument = documentService.getWebDocument(historicalDataUrl, interval.getTtl());
            if(webDocument!=null){

                csvString = webDocument.getContent();
            }

        } catch (Exception exception) {
            logger.error(exception.getMessage(),exception);
        }
        return csvString;
    }


    @Override
    public EquitySummaryResponse getEquitySummary(EquityIdentifier identifier) throws ServiceException {
        EquitySummaryResponse summaryResponse = null;
        String summaryUrl = MessageFormat.format(config.getEnvProperty("YahooQuoteSummary"), identifier.getSymbol());
        try {

            WebDocument webDocument = documentService.getWebDocument(summaryUrl, Period.ofDays(15));
            String responseString = webDocument.getContent();
            if(StringUtils.isNotBlank(responseString)){

                summaryResponse = JsonUtils.deserialize(responseString,EquitySummaryResponse.class);
            }

        } catch (Exception exception) {
            logger.error(exception.getMessage(),exception);
        }
        return summaryResponse;
    }

    @Override
    public ChartResponse getEquityChart(EquityIdentifier equityIdentifier) throws ServiceException {
        ChartResponse chartResponse = null;
        try {
            String url = MessageFormat.format(config.getEnvProperty("StockChartUrl"), equityIdentifier.getSymbol());
            chartResponse = get(url, ChartResponse.class);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
        return chartResponse;
    }


    @Override
    public EquityStockExchangeDetailsResponse getStockExchangeDetails(EquityIdentifier equityIdentifier) throws ServiceException {

        EquityStockExchangeDetailsResponse result = null;
        try {
            String searchString = equityIdentifier.getSearchString();
            String url = MessageFormat.format(config.getEnvProperty("StockExchangeDetailsUrl"), searchString);
            WebDocument webDocument = documentService.getWebDocument(url, Period.ofDays(30));
            if (webDocument != null) {
                EquitySearchResponse searchResponse = JsonUtils.deserialize(webDocument.getContent(), EquitySearchResponse.class);
                if (searchResponse != null) {
                    List<EquityStockExchangeDetailsResponse> stockExchangeDetails1 = searchResponse.getStockExchangeDetails();
                    Optional<EquityStockExchangeDetailsResponse> first = stockExchangeDetails1.stream().filter(detailsResponse -> detailsResponse.getExchange().equals(equityIdentifier.getExchange())).findFirst();
                    if (first.isPresent()) {
                        result = first.get();
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e);
        }

        return result;
    }

    @Override
    public EquityStockExchangeDetails create(CreateStockExchangeDetailsRequest createStockExchangeDetailsRequest) {
        EquityStockExchangeDetails equityStockExchangeDetails = objectConverter.convert(createStockExchangeDetailsRequest);
        return create(equityStockExchangeDetails);
    }


    @Override
    public EquityStockExchangeDetails create(EquityStockExchangeDetails equityStockExchangeDetails) {
        EquityStockExchangeDetails byExchangeAndStockSymbol = stockExchangeDetailsRepository.findByExchangeAndStockSymbol(equityStockExchangeDetails.getExchange(), equityStockExchangeDetails.getSymbol());
        if (byExchangeAndStockSymbol != null) {
            equityStockExchangeDetails.setId(byExchangeAndStockSymbol.getId());

        }
        EquityStockExchangeDetails save = stockExchangeDetailsRepository.save(equityStockExchangeDetails);
        return save;
    }

    @Override
    public EquityStockExchangeDetails deleteById(String id) throws ServiceException {
        AtomicReference<EquityStockExchangeDetails> reference = new AtomicReference<>();
        Optional<EquityStockExchangeDetails> optional = stockExchangeDetailsRepository.findById(id);
        optional.ifPresent(ex -> {
            reference.set(ex);
            stockExchangeDetailsRepository.delete(ex);
        });
        optional.orElseThrow(() -> new ServiceException(MessageFormat.format("EquityStockExchangeDetails not found with id:{}", id)));
        return reference.get();
    }

    @Override
    public EquityStockExchangeDetails deleteByExchangeAndStockSymbol(String exchange, String symbol) throws ServiceException {

        EquityStockExchangeDetails stockExchangeDetails = stockExchangeDetailsRepository.findByExchangeAndStockSymbol(exchange, symbol);
        if (stockExchangeDetails != null) {
            stockExchangeDetailsRepository.delete(stockExchangeDetails);
        } else {
            throw new ServiceException(MessageFormat.format("EquityStockExchangeDetails not found with exchange:{} symbol:{}", exchange, symbol));
        }

        return stockExchangeDetails;
    }

    @Override
    public EquityStockExchangeDetails findById(String id) {
        AtomicReference<EquityStockExchangeDetails> reference = new AtomicReference<>();
        Optional<EquityStockExchangeDetails> optional = stockExchangeDetailsRepository.findById(id);
        optional.ifPresent(ex -> reference.set(ex));
        return reference.get();
    }

    @Override
    public Page<EquityStockExchangeDetails> findByExchange(String exchange, Pageable pageable) {
        Page<EquityStockExchangeDetails> page = stockExchangeDetailsRepository.findByExchange(exchange, pageable);
        return page;
    }

    @Override
    public Page<EquityStockExchangeDetails> findAll(Pageable pageable) {
        Page<EquityStockExchangeDetails> page = stockExchangeDetailsRepository.findAll(pageable);
        return page;
    }

    @Override
    public List<EquityStockExchangeDetails> findByLongName(String longName) {
        return stockExchangeDetailsRepository.findByLongName(longName);
    }

    @Override
    public List<EquityStockExchangeDetails> findByShortName(String shortName) {
        return stockExchangeDetailsRepository.findByLongName(shortName);
    }

    @Override
    public List<EquityStockExchangeDetails> findByName(String name) {
        List<EquityStockExchangeDetails> existingDetails = null;
        existingDetails = stockExchangeDetailsRepository.findByLongName(name);

        if (existingDetails == null || existingDetails.isEmpty()) {
            existingDetails = stockExchangeDetailsRepository.findByShortName(name);

        }

        return existingDetails;
    }

    @Override
    protected List<Interceptor> getInterceptors() {
        return new ArrayList<>(Arrays.asList(rateLimitInterceptor));
    }
}
