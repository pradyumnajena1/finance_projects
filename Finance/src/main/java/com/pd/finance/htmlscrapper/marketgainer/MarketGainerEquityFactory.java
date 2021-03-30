package com.pd.finance.htmlscrapper.marketgainer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pd.finance.htmlscrapper.equity.*;
import com.pd.finance.model.*;
import com.pd.finance.response.EquityStockExchangeDetailsResponse;
import com.pd.finance.service.ICacheService;
import com.pd.finance.service.IDocumentService;
import com.pd.finance.service.IStockExchangeService;
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
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class MarketGainerEquityFactory implements IMarketGainerEquityFactory {
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
    private IMarketGainerEquityPerformancesFactory performancesFactory;
    @Autowired
    private IEquityInsightsFactory insightsFactory;
    @Autowired
    private IDocumentService documentService;
    @Autowired
    private ICacheService cacheService;
    @Autowired
    private IStockExchangeService stockExchangeService;



    @Override
    public   List<Equity> fetchMarketGainerEquities(Document document, int maxEquitiesToFetch, String exchange) throws IOException {
        Queue<Equity> equityCollector = new ConcurrentLinkedQueue<>();
        Node histTable =MarketGainerPageHelper.extractMarketGainersTableNode(document,exchange);
        List<Node> rows =MarketGainerPageHelper.extractMarketGainerRows(histTable);

        if(maxEquitiesToFetch!= CommonUtils.FetchAllEquities){
            rows = rows.subList(0,Math.max(0, Math.min(rows.size(),maxEquitiesToFetch)));
        }

        rows.stream().forEach(rowNode-> extractEquity(equityCollector, rowNode, exchange));


        return new ArrayList<>(equityCollector);
    }

    private void extractEquity(Queue<Equity> equityCollector, Node rowNode, String exchange) {

        try {
            String extractedName = MarketGainerPageHelper.extractName(rowNode);
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
            extractedName = MarketGainerPageHelper.extractName(rowNode);
            logger.info("createEquitiesFromMarketGainerPageRow exec started for equity extractedName {}",extractedName);


            EquityStockExchangeDetailsResponse stockExchangeDetails = stockExchangeService.getStockExchangeDetails(equityIdentifier);

           if( stockExchangeDetails!=null){

               result =   createEquity(rowNode, stockExchangeDetails);
           }else {

               logger.info("createEquitiesFromMarketGainerPageRow could not find exchange details  for equity extractedName {}",extractedName);
           }
            logger.info("createEquitiesFromMarketGainerPageRow exec completed for equity extractedName {}",extractedName);
        } catch (Exception ex) {
            logger.error("createEquitiesFromMarketGainerPageRow exec failed for equity extractedName {}",extractedName,ex);
        }
        return result;
    }

    private Equity createEquity(Node rowNode, EquityStockExchangeDetailsResponse details) {
        Equity equity = new Equity();

        SourceDetails sourceDetails = getSourceDetails(rowNode);
        equity.getSourceDetails().addSourceDetails(sourceDetails);

        EquityIdentifiers equityIdentifiers =  getEquityIdentifiers(rowNode,equity,details);
        equity.setEquityIdentifiers(equityIdentifiers);

        logger.info("createEquity completed creating equity {}",equity.getEquityIdentifiers());
        return equity;
    }

    private EquityIdentifiers getEquityIdentifiers(Node rowNode, Equity equity, EquityStockExchangeDetailsResponse details) {

        EquityIdentifiers equityIdentifiers = new EquityIdentifiers();

        String equityName =MarketGainerPageHelper.extractName(rowNode);
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
        sourceDetails.setEquityName(MarketGainerPageHelper.extractName(rowNode));
        sourceDetails.setSourceUrl(extractUrl(rowNode));
        sourceDetails.setSourceName(Constants.SOURCE_MONEY_CONTROL);
        return sourceDetails;
    }


    private   String extractUrl(Node rowNode) {
        Node nameCell = MarketGainerPageHelper.getAttributeCell(rowNode, 0);
        return nameCell.childNode(0).childNode(0).attr("href");
    }


}
