package com.pd.finance.htmlscrapper.marketgainer;

import com.pd.finance.model.EquityCurrentPriceStats;
import com.pd.finance.model.EquityPerformances;
import org.jsoup.nodes.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.stream.Collectors;
@Component
public class EquityCurrentPriceStatsFactory implements IEquityCurrentPriceStatsFactory {
    private static final Logger logger = LoggerFactory.getLogger(EquityCurrentPriceStatsFactory.class);


    @Override
    public EquityCurrentPriceStats createEquityCurrentPriceStats(Node rowNode){
        EquityCurrentPriceStats currentPriceStats = new EquityCurrentPriceStats();
        currentPriceStats.setLow(extractLow(rowNode));
        currentPriceStats.setHigh(extractHigh(rowNode));
        currentPriceStats.setLastPrice(extractLastPrice(rowNode));
        currentPriceStats.setPrevClose(extractPrevClose(rowNode));
        currentPriceStats.setChange(extractChange(rowNode));
        currentPriceStats.setPercentageGain(extractPercentageGain(rowNode));
        return currentPriceStats;
    }

    private BigDecimal extractPercentageGain(Node rowNode) {
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
}
