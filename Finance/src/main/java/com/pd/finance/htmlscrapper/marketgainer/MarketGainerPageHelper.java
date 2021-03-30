package com.pd.finance.htmlscrapper.marketgainer;

import com.pd.finance.utils.Constants;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class MarketGainerPageHelper {
    private static final Logger logger = LoggerFactory.getLogger(MarketGainerPageHelper.class);


    public static Node extractMarketGainersTableNode(Document document, String exchange)   {
        Element histTableContainerDiv = null;
        Node result = null;
        if(Constants.EXCHANGE_NSI.equalsIgnoreCase(exchange)){
             histTableContainerDiv = document.select("#mc_content > section > section > div.clearfix.stat_container > div.columnst.FR.wbg.brdwht > div > div > div.bsr_table.hist_tbl_hm").first();

       }else{
            histTableContainerDiv = document.select("#mc_content > section > section > div.clearfix.stat_container > div.columnst.FR.wbg.brdwht > div > div > div.bsr_table.hist_tbl_hm").first();

       }
        if(histTableContainerDiv!=null){
            result = histTableContainerDiv.childNode(3);
        }
        return result;
    }

    public static  Node getEquityRow(String equityName, Document document,String exchange)  {
        Node histTable = MarketGainerPageHelper.extractMarketGainersTableNode(document, exchange);
        List<Node> rows = MarketGainerPageHelper.extractMarketGainerRows(histTable);
        List<Node> collect = rows.stream().filter(aRow ->MarketGainerPageHelper.extractName(aRow).equalsIgnoreCase(equityName)).collect(Collectors.toList());
        return collect.get(0);
    }

    public static Node getAttributeCell(Node rowNode, int index) {
        return rowNode.childNodes().stream().filter(aNode-> isAttributeCell(aNode)).collect(Collectors.toList()).get(index);
    }

    public  static boolean isAttributeCell(Node aNode) {
        return aNode.nodeName().equalsIgnoreCase("td");
    }

    public static  BigDecimal extractMoneyFromTableCell(Node tableCell) {
        try {
            String textValue = tableCell.childNode(0).attr("text");
            textValue = textValue.replace(",","");
            return new BigDecimal( textValue);
        } catch (NumberFormatException ex) {
            logger.error("extractMoneyFromTableCell exec failed",ex);
            return BigDecimal.ZERO;
        }
    }

    public static List<Node> extractMarketGainerRows(Node histTable) {
        Node tbody = histTable.childNodes().stream().filter(aNode -> aNode.nodeName()
                .equalsIgnoreCase("tbody"))
                .collect(Collectors.toList()).get(0);
        List<Node> rows = tbody.childNodes().stream().filter(aNode -> aNode.nodeName().equalsIgnoreCase("tr")).collect(Collectors.toList());
        return rows;


    }
    public static   String extractName(Node rowNode) {
        Node nameCell = getAttributeCell(rowNode, 0);
        return nameCell.childNode(0).childNode(0).childNode(0).attr("text");
    }

}
