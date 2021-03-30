package com.pd.finance.service;

import com.pd.finance.config.ApplicationConfig;
import com.pd.finance.exceptions.PersistenceException;
import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityHistoricalData;
import com.pd.finance.model.EquityHistoricalDataLineItem;
import com.pd.finance.model.EquityIdentifier;
import com.pd.finance.response.EquityStockExchangeDetailsResponse;
import com.pd.finance.utils.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.*;

@Service
public class HistoricalStockPriceService extends AbstractHttpService implements IHistoricalStockPriceService {
    private static final Logger logger = LoggerFactory.getLogger(EquityHistoricalStockPriceAttributeService.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private ApplicationConfig config;
    @Autowired
    private IEquityService equityService;

    @Autowired
    private EquityHistoricalStockPriceAttributeService historicalStockPriceAttributeService;

    @Override
    public Equity updateEquityHistoricalStockPrice(Equity equity) throws ServiceException{
        try {

            EquityIdentifier identifier = equity.getDefaultEquityIdentifier();
            historicalStockPriceAttributeService.enrichEquity(identifier,equity);
            equityService.upsertEquity(equity);
            return equity;

        } catch (ServiceException | PersistenceException e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(e.getMessage(),e);
        }
    }

    @Override
    public void updateAllEquityHistoricalStockPrice() throws ServiceException{
        try {

            List<Equity> equities = equityService.getEquities();
            for(Equity anEquity:equities){

                try {
                    updateEquityHistoricalStockPrice(anEquity);
                    logger.info("successfully updated historical stock price of equity {}",anEquity.getDefaultEquityIdentifier());
                } catch (ServiceException e) {
                    logger.info("failed to update historical stock price of equity {}",anEquity.getDefaultEquityIdentifier());
                }
            }


        } catch (PersistenceException e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(e.getMessage(),e);
        }
    }


    @Override
    public EquityHistoricalData getHistoricalStockPrice(EquityIdentifier identifier, TemporalAmount amountToSubtract) throws ServiceException {
        logger.info("getHistoricalStockPrice exec started for equity {}",identifier);
        EquityHistoricalData historicalData = null;
        try {
            Equity equity = equityService.getEquity(identifier);
            EquityStockExchangeDetailsResponse stockExchangeDetails = equity.getStockExchangeDetails();
            if (stockExchangeDetails != null) {
                EquityHistoricalData existinghistoricalData = equity.getHistoricalData();
                String historicalDataString = getHistoricalPriceCsvString(amountToSubtract, stockExchangeDetails,existinghistoricalData);
                if (StringUtils.isNotBlank(historicalDataString)) {

                    List<EquityHistoricalDataLineItem> lineItemsCollector = new ArrayList<>();

                    collectExistingLineItems(existinghistoricalData, lineItemsCollector);

                    collectNewLineItems(historicalDataString, lineItemsCollector);

                    if (CollectionUtils.isNotEmpty(lineItemsCollector)) {
                        historicalData = getHistoricalData(lineItemsCollector);
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
    protected EquityHistoricalData getHistoricalData(List<EquityHistoricalDataLineItem> lineItems) {
        EquityHistoricalData historicalData;
        Collections.sort(lineItems);
        EquityHistoricalDataLineItem first = lineItems.get(0);
        EquityHistoricalDataLineItem last = lineItems.get(lineItems.size() - 1);
        historicalData = new EquityHistoricalData(first.getDate(), last.getDate());
        historicalData.addLineItems(lineItems);
        return historicalData;
    }

    protected void collectExistingLineItems(EquityHistoricalData existinghistoricalData, List<EquityHistoricalDataLineItem> lineItems) {
        if(existinghistoricalData!=null){
            lineItems.addAll(existinghistoricalData.getLineItems());
        }
    }

    protected void collectNewLineItems(String historicalDataString, List<EquityHistoricalDataLineItem> lineItems) throws IOException {
        try (Reader reader = new StringReader(historicalDataString);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
            for (CSVRecord record : csvParser) {

                EquityHistoricalDataLineItem equityHistoricalDataLineItem = getEquityHistoricalDataLineItem(record);
                lineItems.add(equityHistoricalDataLineItem);

            }
        }
    }

    @NotNull
    protected EquityHistoricalDataLineItem getEquityHistoricalDataLineItem(CSVRecord record) {
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

    protected String getHistoricalPriceCsvString(TemporalAmount amountToSubtract, EquityStockExchangeDetailsResponse stockExchangeDetails, EquityHistoricalData existinghistoricalData) throws ServiceException {

        Instant startTime = getStartTime(amountToSubtract, existinghistoricalData);
        Instant endTime = Instant.now();

        long period1 = startTime.getEpochSecond();
        long period2 = endTime.getEpochSecond();

        String historicalPriceCsvString = getHistoricalPriceCsvString(stockExchangeDetails, period1, period2);
        if (historicalPriceCsvString == null) {
            logger.info("Historical data no available from start time {},getting max available", startTime);
            period1 = 0L;
            historicalPriceCsvString = getHistoricalPriceCsvString(stockExchangeDetails, period1, period2);
        }
        return historicalPriceCsvString;
    }

    private Instant getStartTime(TemporalAmount amountToSubtract, EquityHistoricalData existinghistoricalData) {
        Instant startTime = null;
        if(existinghistoricalData!=null){
            List<EquityHistoricalDataLineItem> lineItems = existinghistoricalData.getLineItems();
            Collections.sort(lineItems);
            long endTime = lineItems.get(lineItems.size() - 1).getDate().getTime();
              startTime = Instant.ofEpochMilli(endTime);
        }else {
            startTime = Instant.now().minus(amountToSubtract);
        }
        return startTime;
    }

    private String getHistoricalPriceCsvString(EquityStockExchangeDetailsResponse stockExchangeDetails, long period1, long period2) throws ServiceException {
        String historicalDataUrl = MessageFormat.format(config.getEnvProperty("HistoricalDataUrl"), stockExchangeDetails.getSymbol(), String.valueOf(period1), String.valueOf(period2), "1d", true);
        return get(historicalDataUrl);
    }
}
