package com.pd.finance.htmlscrapper.marketloser;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pd.finance.config.ApplicationConfig;
import com.pd.finance.htmlscrapper.equity.*;
import com.pd.finance.htmlscrapper.marketgainer.MarketGainerEquityFactory;
import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityIdentifier;
import com.pd.finance.model.EquityIdentifiers;
import com.pd.finance.model.SourceDetails;
import com.pd.finance.response.EquityStockExchangeDetailsResponse;
import com.pd.finance.service.ICacheService;
import com.pd.finance.service.IDocumentService;
import com.pd.finance.service.IYahooService;
import com.pd.finance.utils.CommonUtils;
import com.pd.finance.utils.Constants;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MarketLoserEquityFactory implements IMarketLoserEquityFactory{
    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(MarketGainerEquityFactory.class);


    @Autowired
    private IEquityOverviewFactory overviewFactory;
    @Autowired
    private IEquityEssentialsFactory essentialsFactory;
    @Autowired
    private IEquitySwotFactory swotFactory;
    @Autowired
    private IEquityTechnicalDetailsFactory technicalDetailsFactory;

    @Autowired
    private IEquityInsightsFactory insightsFactory;
    @Autowired
    private IDocumentService documentService;
    @Autowired
    private ICacheService cacheService;
    @Autowired
    private IYahooService stockExchangeService;
    @Autowired
    private ApplicationConfig config;


    @Override
    public List<Equity> fetchMarketLoserEquities(Document document, int maxEquitiesToFetch, String exchange) throws IOException {
        Queue<Equity> equityCollector = new ConcurrentLinkedQueue<>();
        Node histTable = MarketLoserPageHelper.extractMarketLosersTableNode(document,exchange);
        List<Node> rows =MarketLoserPageHelper.extractMarketLoserRows(histTable);
        logger.info("fetchMarketLoserEquities found {} rows ",rows.size());

        if(maxEquitiesToFetch!= CommonUtils.FetchAllEquities){
            rows = rows.subList(0,Math.max(0, Math.min(rows.size(),maxEquitiesToFetch)));
        }
        final int numEquitiesToCreate = rows.size();
        final AtomicInteger numEquitiesCreated = new AtomicInteger(0);
        rows.stream().forEach(rowNode-> {
            try {
                extractEquity(equityCollector, rowNode, exchange);
                delay();
                int equitiesCreated = numEquitiesCreated.incrementAndGet();
                logger.info("{} equities created out of {} ",equitiesCreated,numEquitiesToCreate);

            } catch (InterruptedException e) {
                logger.error(e.getMessage(),e);
            }
        });


        return new ArrayList<>(equityCollector);
    }
    protected void delay() throws InterruptedException {
        try {
            Thread.sleep(Integer.parseInt(config.getEnvProperty("LoserPageCrawlerDelayInMillis")));
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }

    private void extractEquity(Queue<Equity> equityCollector, Node rowNode, String exchange) {

        try {
            String extractedName = MarketLoserPageHelper.extractName(rowNode);
            Equity equity = createEquityFromMarketGainerPageRow(rowNode, new EquityIdentifier(extractedName, exchange, Constants.SOURCE_MONEY_CONTROL));

            if(equity!=null){
                equityCollector.add(equity);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }

    private   Equity createEquityFromMarketGainerPageRow(Node rowNode, EquityIdentifier equityIdentifier) {

        Equity result = null;
        String extractedName = null;
        try {
            extractedName = MarketLoserPageHelper.extractName(rowNode);
            logger.info("createEquitiesFromMarketLoserPageRow exec started for equity extractedName {}",extractedName);


            EquityStockExchangeDetailsResponse stockExchangeDetails = stockExchangeService.getStockExchangeDetails(equityIdentifier);

            if( stockExchangeDetails!=null){

                result =   createEquity(rowNode, stockExchangeDetails);
            }else {

                logger.info("createEquitiesFromMarketLoserPageRow could not find exchange details  for equity extractedName {}",extractedName);
            }
            logger.info("createEquitiesFromMarketLoserPageRow exec completed for equity extractedName {}",extractedName);
        } catch (Exception ex) {
            logger.error("createEquitiesFromMarketLoserPageRow exec failed for equity extractedName {}",extractedName,ex);
        }
        return result;
    }

    private Equity createEquity(Node rowNode, EquityStockExchangeDetailsResponse details) {
        Equity equity = new Equity();

        SourceDetails sourceDetails = getSourceDetails(rowNode);
        equity.getSourceDetails().addSourceDetails(sourceDetails);

        EquityIdentifiers equityIdentifiers =  getEquityIdentifiers(rowNode,equity,details);
        equity.setEquityIdentifiers(equityIdentifiers);

        equity.setStockExchangeDetails(details);

        logger.info("createEquity completed creating equity {}",equity.getEquityIdentifiers());
        return equity;
    }

    private EquityIdentifiers getEquityIdentifiers(Node rowNode, Equity equity, EquityStockExchangeDetailsResponse details) {

        EquityIdentifiers equityIdentifiers = new EquityIdentifiers();

        String equityName =MarketLoserPageHelper.extractName(rowNode);
        if(StringUtils.isBlank(equityName)){
            equityName =  StringUtils.isNotBlank( details.getShortName())?details.getShortName():details.getLongName();
        }


        EquityIdentifier equityIdentifier = new EquityIdentifier(equityName,details.getExchange(),Constants.SOURCE_MONEY_CONTROL);
        equityIdentifier.setSymbol(details.getSymbol());

        logger.info("createEquity started creating equity {}",equityName);


        equityIdentifiers.getIdentifiers().add(equityIdentifier);
        return equityIdentifiers;
    }

    @NotNull
    private SourceDetails getSourceDetails(Node rowNode) {
        SourceDetails sourceDetails = new SourceDetails();
        sourceDetails.setEquityName(MarketLoserPageHelper.extractName(rowNode));
        sourceDetails.setSourceUrl(extractUrl(rowNode));
        sourceDetails.setSourceName(Constants.SOURCE_MONEY_CONTROL);
        return sourceDetails;
    }


    private   String extractUrl(Node rowNode) {
        Node nameCell = MarketLoserPageHelper.getAttributeCell(rowNode, 0);
        return nameCell.childNode(0).childNode(0).attr("href");
    }

}
