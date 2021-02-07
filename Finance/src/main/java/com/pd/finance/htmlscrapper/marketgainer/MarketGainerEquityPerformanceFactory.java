package com.pd.finance.htmlscrapper.marketgainer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pd.finance.model.EquityPerformance;
import org.jsoup.nodes.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;

public class MarketGainerEquityPerformanceFactory {
    @JsonIgnore
    private static Logger logger = LoggerFactory.getLogger(MarketGainerEquityPerformanceFactory.class);
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
    public static EquityPerformance create(Node performanceNode){
         EquityPerformance performance = new EquityPerformance();
         performance.setDate(extractDate(performanceNode));
        performance.setPrice(extractPrice(performanceNode));
         performance.setChange(extractChange(performanceNode));
         performance.setChangePercent(extractChangePercent(performanceNode));


         return performance;
    }

    private static BigDecimal extractPrice(Node performanceNode) {
        try {
            Node firstDiv = getChildNode(performanceNode, "div", 0);
            Node secondP = getChildNode(firstDiv, "p", 1);
            Node firstStrong = getChildNode(secondP, "strong", 0);
            Node textNode = firstStrong.childNode(0);
            Node performancePriceCell = textNode;
            return extractDecimalFromPerformanceCell(performancePriceCell);
        } catch (Exception e) {
           logger.error(e.getMessage(),e);
           return null;
        }
    }

    private static Date extractDate(Node performanceNode) {
        try {
            Node firstDiv = getChildNode(performanceNode, "div", 0);
            Node firstP = getChildNode(firstDiv, "p", 0);
            Node performanceDateCell = firstP;
            return extractDateFromPerformanceCell(performanceDateCell);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return null;
        }
    }



    private static BigDecimal extractChangePercent(Node performanceNode) {
        try {
            Node firstDiv = getChildNode(performanceNode, "div", 0);
            Node secondP = getChildNode(firstDiv, "p", 1);
            Node secondSpan = getChildNode(secondP, "span", 1);
            Node textNode = secondSpan.childNode(0);
            String text = textNode.attr("text");
            String  changePercentText = text.substring(text.indexOf("(")+1,text.indexOf("%"));
            return extractDecimalFromText(changePercentText);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return null;
        }
    }

    private static BigDecimal extractChange(Node performanceNode) {
        try {
            Node firstDiv = getChildNode(performanceNode, "div", 0);
            Node secondP = getChildNode(firstDiv, "p", 1);
            Node secondSpan = getChildNode(secondP, "span", 1);
            Node textNode = secondSpan.childNode(0);

            String text = textNode.attr("text");
            String  changeText = text.substring(0,text.indexOf("("));
            return extractDecimalFromText(changeText);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return null;
        }
    }


    private static BigDecimal extractDecimalFromPerformanceCell(Node performanceDecimalTextNode) {

            String textValue = performanceDecimalTextNode.attr("text");

            return extractDecimalFromText( textValue);

    }
    private static Date extractDateFromPerformanceCell(Node performanceDateTextNode) {

        String textValue = performanceDateTextNode.childNode(0).attr("text");

        return extractDateFromText( textValue);

    }
    private static BigDecimal extractDecimalFromText(String textValue) {
        try {

            textValue = textValue.replace(",","");
            textValue = textValue.trim();
            return new BigDecimal( textValue);
        } catch (NumberFormatException ex) {
            logger.error("extractDecimalFromPerformanceCell exec failed",ex);
            return BigDecimal.ZERO;
        }
    }



    private static Date extractDateFromText(String textValue) {
        try {

            textValue = textValue.trim();
            return dateFormat.parse( textValue);
        } catch (ParseException  ex) {
            logger.error("extractDateFromPerformanceCell exec failed",ex);
            return null;
        }
    }

    private static Node getChildNode(Node parentNode,String childNodeTagName,int index) {
        return parentNode. childNodes().stream().filter(aNode -> aNode.nodeName().equalsIgnoreCase(childNodeTagName)).collect(Collectors.toList()).get(index);

    }
}
