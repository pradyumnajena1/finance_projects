package com.pd.finance.htmlscrapper.marketgainer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pd.finance.model.EquityPerformance;
import com.pd.finance.model.EquityPerformances;
import org.jsoup.nodes.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MarketGainerEquityPerformancesFactory {
    @JsonIgnore
    private static Logger logger = LoggerFactory.getLogger(MarketGainerEquityPerformancesFactory.class);


    public static EquityPerformances createMarketGainerEquityPerformances(Node rowNode){
       Node performancesNode = getPerformancesNode(rowNode);
       List<EquityPerformance> performances = new ArrayList<>();
       for(int i=0;i<5;i++){
           Node performanceNode = getPerformanceNode(performancesNode, i);

           performances.add(MarketGainerEquityPerformanceFactory.create(performanceNode));
       }
        EquityPerformances equityPerformances =new EquityPerformances("Last 5 day performance", performances );
       return equityPerformances;
    }

    private static Node getPerformancesNode(Node rowNode){
       return rowNode.childNodes().stream().filter(aNode-> isPerformancesCell(aNode)).collect(Collectors.toList()).get(0);
    }

    private static boolean isPerformancesCell(Node aNode) {
        return aNode.nodeName().equalsIgnoreCase("td")&&aNode.attr("class").equalsIgnoreCase("performance");
    }

    private static Node getPerformanceNode(Node performancesNode, int index) {
        return performancesNode.childNodes().stream().filter(aNode-> isPerformanceNode(aNode)).collect(Collectors.toList()).get(index);
    }

    private static boolean isPerformanceNode(Node aNode) {
        return aNode.nodeName().equalsIgnoreCase("div");
    }
}
