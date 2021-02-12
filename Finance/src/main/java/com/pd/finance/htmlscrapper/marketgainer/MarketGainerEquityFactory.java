package com.pd.finance.htmlscrapper.marketgainer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pd.finance.htmlscrapper.equity.*;
import com.pd.finance.model.*;
import com.pd.finance.service.ICacheService;
import com.pd.finance.service.IDocumentService;
import com.pd.finance.utils.CommonUtils;
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


    @Override
    public   List<Equity> fetchMarketGainerEquities(Document document,int maxEquitiesToFetch) throws IOException {
        Queue<Equity> equityCollector = new ConcurrentLinkedQueue<>();
        Node histTable =MarketGainerPageHelper.extractMarketGainersTableNode(document);
        List<Node> rows =MarketGainerPageHelper.extractMarketGainerRows(histTable);

        if(maxEquitiesToFetch!= CommonUtils.FetchAllEquities){
            rows = rows.subList(0,Math.max(0, Math.min(rows.size(),maxEquitiesToFetch)));
        }

        rows.stream().forEach(rowNode-> extractEquity(equityCollector, rowNode));

        return new ArrayList<>(equityCollector);
    }

    private void extractEquity(Queue<Equity> equityCollector, Node rowNode) {

        try {
            Equity equity = createEquityFromMarketGainerPage(rowNode);

            equityCollector.add(equity);
        } catch (Exception e) {
          logger.error(e.getMessage(),e);
        }
    }

    private   Equity createEquityFromMarketGainerPage(Node rowNode) {

        Equity equity = new Equity();
        try {
            equity.setName(MarketGainerPageHelper.extractName(rowNode));
            logger.info("started creating equity {}",equity.getName());
            equity.setUrl(extractUrl(rowNode));
            logger.info("completed creating equity {}",equity.getName());
        } catch (Exception ex) {
            logger.error("create exec failed",ex);
        }
        return equity;
    }



    private   String extractUrl(Node rowNode) {
        Node nameCell = MarketGainerPageHelper.getAttributeCell(rowNode, 0);
        return nameCell.childNode(0).childNode(0).attr("href");
    }


}
