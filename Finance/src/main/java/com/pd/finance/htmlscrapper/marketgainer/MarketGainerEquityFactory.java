package com.pd.finance.htmlscrapper.marketgainer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pd.finance.htmlscrapper.equity.EquityEssentialsFactory;
import com.pd.finance.htmlscrapper.equity.EquityOverviewFactory;
import com.pd.finance.htmlscrapper.equity.EquitySwotFactory;
import com.pd.finance.htmlscrapper.equity.EquityTechnicalDetailsFactory;
import com.pd.finance.model.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;


public class MarketGainerEquityFactory {
    @JsonIgnore
    private static Logger logger = LoggerFactory.getLogger(MarketGainerEquityFactory.class);


    public static List<Equity> fetchMarketGainerEquities(Document document) throws IOException {
        Queue<Equity> equityCollector = new ConcurrentLinkedQueue<>();
        Node histTable = extractMarketGainersTableNode(document);
        List<Node> rows = extractMarketGainerRows(histTable);

        rows.stream().forEach(rowNode->{

                Equity equity = createMarketGainerEquity(rowNode);

                enrichEquity(equity);
                equityCollector.add(equity);


        });

        return new ArrayList<>(equityCollector);
    }

    private static void enrichEquity(Equity equity ) {
        try {
            addSwotDetails(equity);
            addEssentialDetails(equity);
            addEquityOverview(equity);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }

    private static void addEquityOverview(Equity equity) {
        try {
            Document document = Jsoup.connect(equity.getUrl()).get();
            EquityOverview overview =  EquityOverviewFactory.create(document);
            equity.setOverview(overview);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
    }

    private static void addEssentialDetails(Equity equity) {
        try {
            Document document = Jsoup.connect(equity.getUrl()).get();
            EquityEssentials essentials =  EquityEssentialsFactory.create(document);
            equity.setEssentials(essentials);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
    }

    private static void addSwotDetails(Equity equity) {


        try {
            Document document = Jsoup.connect(equity.getUrl()).get();
            EquitySwotDetails  swotDetails =  EquitySwotFactory.create(document);
            equity.setSwotDetails(swotDetails);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }



    }

    private static Node extractMarketGainersTableNode(Document document) throws IOException {

        Element histTableContainerDiv = document.select("div.bsr_table.hist_tbl_hm").get(0);
        return histTableContainerDiv.childNode(3);
    }

    private static Equity createMarketGainerEquity(Node rowNode) {

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

    private static EquityPerformances extractPerformances(Node rowNode) {

         return  MarketGainerEquityPerformancesFactory.createMarketGainerEquityPerformances(rowNode);

    }

    private static BigDecimal extractPercentageGain(Node rowNode) {
        Node prevCloseCell = getAttributeCell(rowNode, 6);
        return extractMoneyFromTableCell(prevCloseCell);
    }

    private static BigDecimal extractChange(Node rowNode) {
        Node prevCloseCell = getAttributeCell(rowNode, 5);
        return extractMoneyFromTableCell(prevCloseCell);
    }

    private static BigDecimal extractPrevClose(Node rowNode) {
        Node prevCloseCell = getAttributeCell(rowNode, 4);
        return extractMoneyFromTableCell(prevCloseCell);
    }



    private static BigDecimal extractLastPrice(Node rowNode) {
        Node lastPriceCell = getAttributeCell(rowNode, 3);
        return extractMoneyFromTableCell(lastPriceCell);

    }

    private static BigDecimal extractHigh(Node rowNode) {
        Node highCell = getAttributeCell(rowNode, 1);
        return extractMoneyFromTableCell(highCell);

    }

    private static BigDecimal extractLow(Node rowNode) {
        try {
            Node lowCell = getAttributeCell(rowNode, 2);
            return extractMoneyFromTableCell(lowCell);
        } catch (Exception ex) {
            logger.error("extractLow exec failed",ex);
            return null;
        }

    }

    private static String extractName(Node rowNode) {
        Node nameCell = getAttributeCell(rowNode, 0);
        return nameCell.childNode(0).childNode(0).childNode(0).attr("text");
    }

    private static String extractUrl(Node rowNode) {
        Node nameCell = getAttributeCell(rowNode, 0);
        return nameCell.childNode(0).childNode(0).attr("href");
    }

    private static Node getAttributeCell(Node rowNode, int index) {
        return rowNode.childNodes().stream().filter(aNode-> isAttributeCell(aNode)).collect(Collectors.toList()).get(index);
    }

    private static boolean isAttributeCell(Node aNode) {
        return aNode.nodeName().equalsIgnoreCase("td");
    }

    private static BigDecimal extractMoneyFromTableCell(Node tableCell) {
        try {
            String textValue = tableCell.childNode(0).attr("text");
            textValue = textValue.replace(",","");
            return new BigDecimal( textValue);
        } catch (NumberFormatException ex) {
            logger.error("extractMoneyFromTableCell exec failed",ex);
            return BigDecimal.ZERO;
        }
    }

    private static List<Node> extractMarketGainerRows(Node histTable) {
        Node tbody = histTable.childNodes().stream().filter(aNode -> aNode.nodeName()
                .equalsIgnoreCase("tbody"))
                .collect(Collectors.toList()).get(0);
        List<Node> rows = tbody.childNodes().stream().filter(aNode -> aNode.nodeName().equalsIgnoreCase("tr")).collect(Collectors.toList());
        return rows;


    }
}
