package com.pd.finance.htmlscrapper.marketgainer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pd.finance.htmlscrapper.equity.*;
import com.pd.finance.model.*;
import com.pd.finance.service.ICacheService;
import com.pd.finance.service.IDocumentService;
import com.pd.finance.utils.CommonUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

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
        Node histTable = extractMarketGainersTableNode(document);
        List<Node> rows = extractMarketGainerRows(histTable);
        if(maxEquitiesToFetch!= CommonUtils.FetchAllEquities){
            rows = rows.subList(0,Math.max(0, Math.min(rows.size(),maxEquitiesToFetch)));
        }

        rows.stream().forEach(rowNode->processEquityRowNode(equityCollector, rowNode));

        return new ArrayList<>(equityCollector);
    }

    private void processEquityRowNode(Queue<Equity> equityCollector, Node rowNode) {

        try {
            Equity equity = createMarketGainerEquity(rowNode);
            Equity enrichedEquity =  cacheService.getEnrichedEquity(equity,anEquity ->  enrichEquity(anEquity));
            equityCollector.add(enrichedEquity);
        } catch (Exception e) {
          logger.error(e.getMessage(),e);
        }
    }

    private   Equity enrichEquity(Equity equity ) {
        try {
            addBasicDetails(equity);
            addSwotDetails(equity);
            addEssentialDetails(equity);
            addEquityOverview(equity);
            addTechnicalDetails(equity);
            addEquityInsights(equity);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return equity;
    }

    private void addBasicDetails(Equity equity) {
        try {
            Document document = documentService.getDocument(equity.getUrl());

             equity.setBseId(document.select("input#bseid").first().attr("value"));
            equity.setNseId(document.select("input#nseid").first().attr("value"));
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }

    private void addEquityInsights(Equity equity) {
        try {
            Document document = documentService.getDocument(equity.getUrl());
            EquityInsights equityInsights =  insightsFactory.create(document);
            equity.setInsights(equityInsights);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }

    private void addTechnicalDetails(Equity equity) {
        try {
            Document document = documentService.getDocument(equity.getUrl());
            TechnicalDetails technicalDetails =  technicalDetailsFactory.create(document);
            equity.setTechnicalDetails(technicalDetails);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }

    private   void addEquityOverview(Equity equity) {
        try {
            Document document = documentService.getDocument(equity.getUrl());
            EquityOverview overview =  overviewFactory.create(document);
            equity.setOverview(overview);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }

    private   void addEssentialDetails(Equity equity) {
        try {
            Document document = documentService.getDocument(equity.getUrl());
            EquityEssentials essentials =  essentialsFactory.create(document);
            equity.setEssentials(essentials);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }

    private   void addSwotDetails(Equity equity) {


        try {
            Document document = Jsoup.connect(equity.getUrl()).get();
            EquitySwotDetails  swotDetails =  swotFactory.create(document);
            equity.setSwotDetails(swotDetails);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }



    }

    private   Node extractMarketGainersTableNode(Document document) throws IOException {

        Element histTableContainerDiv = document.select("div.bsr_table.hist_tbl_hm").get(0);
        return histTableContainerDiv.childNode(3);
    }

    private   Equity createMarketGainerEquity(Node rowNode) {

        Equity equity = new Equity();
        try {
            equity.setName(extractName(rowNode));
            logger.info("started creating equity {}",equity.getName());
            equity.setUrl(extractUrl(rowNode));
            equity.setLow(extractLow(rowNode));
            equity.setHigh(extractHigh(rowNode));
            equity.setLastPrice(extractLastPrice(rowNode));
            equity.setPrevClose(extractPrevClose(rowNode));
            equity.setChange(extractChange(rowNode));
            equity.setPercentageGain(extractPercentageGain(rowNode));
            equity.setPerformances(extractPerformances(rowNode));
            logger.info("completed creating equity {}",equity.getName());
        } catch (Exception ex) {
            logger.error("create exec failed",ex);
        }
        return equity;
    }

    private   EquityPerformances extractPerformances(Node rowNode) {

         return  performancesFactory.createMarketGainerEquityPerformances(rowNode);

    }

    private   BigDecimal extractPercentageGain(Node rowNode) {
        Node prevCloseCell = getAttributeCell(rowNode, 6);
        return extractMoneyFromTableCell(prevCloseCell);
    }

    private   BigDecimal extractChange(Node rowNode) {
        Node prevCloseCell = getAttributeCell(rowNode, 5);
        return extractMoneyFromTableCell(prevCloseCell);
    }

    private   BigDecimal extractPrevClose(Node rowNode) {
        Node prevCloseCell = getAttributeCell(rowNode, 4);
        return extractMoneyFromTableCell(prevCloseCell);
    }



    private   BigDecimal extractLastPrice(Node rowNode) {
        Node lastPriceCell = getAttributeCell(rowNode, 3);
        return extractMoneyFromTableCell(lastPriceCell);

    }

    private   BigDecimal extractHigh(Node rowNode) {
        Node highCell = getAttributeCell(rowNode, 1);
        return extractMoneyFromTableCell(highCell);

    }

    private   BigDecimal extractLow(Node rowNode) {
        try {
            Node lowCell = getAttributeCell(rowNode, 2);
            return extractMoneyFromTableCell(lowCell);
        } catch (Exception ex) {
            logger.error("extractLow exec failed",ex);
            return null;
        }

    }

    private   String extractName(Node rowNode) {
        Node nameCell = getAttributeCell(rowNode, 0);
        return nameCell.childNode(0).childNode(0).childNode(0).attr("text");
    }

    private   String extractUrl(Node rowNode) {
        Node nameCell = getAttributeCell(rowNode, 0);
        return nameCell.childNode(0).childNode(0).attr("href");
    }

    private   Node getAttributeCell(Node rowNode, int index) {
        return rowNode.childNodes().stream().filter(aNode-> isAttributeCell(aNode)).collect(Collectors.toList()).get(index);
    }

    private   boolean isAttributeCell(Node aNode) {
        return aNode.nodeName().equalsIgnoreCase("td");
    }

    private   BigDecimal extractMoneyFromTableCell(Node tableCell) {
        try {
            String textValue = tableCell.childNode(0).attr("text");
            textValue = textValue.replace(",","");
            return new BigDecimal( textValue);
        } catch (NumberFormatException ex) {
            logger.error("extractMoneyFromTableCell exec failed",ex);
            return BigDecimal.ZERO;
        }
    }

    private   List<Node> extractMarketGainerRows(Node histTable) {
        Node tbody = histTable.childNodes().stream().filter(aNode -> aNode.nodeName()
                .equalsIgnoreCase("tbody"))
                .collect(Collectors.toList()).get(0);
        List<Node> rows = tbody.childNodes().stream().filter(aNode -> aNode.nodeName().equalsIgnoreCase("tr")).collect(Collectors.toList());
        return rows;


    }
}
