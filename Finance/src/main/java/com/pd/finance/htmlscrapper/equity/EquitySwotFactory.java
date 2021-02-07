package com.pd.finance.htmlscrapper.equity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pd.finance.model.EquitySwotDetails;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EquitySwotFactory {


    @JsonIgnore
    private static Logger logger = LoggerFactory.getLogger(EquitySwotFactory.class);
    public static EquitySwotDetails create(Document document){

        EquitySwotDetails swotDetails = new EquitySwotDetails();


        swotDetails.setStrengths(extractStrengths(document));
        swotDetails.setOpportunities(extractOpportunities(document));
        swotDetails.setWeaknesses(extractWeaknesses(document));
        swotDetails.setThreats(extractThreats(document));


        return swotDetails;
    }





    private static List<String> extractThreats(Document document) {
        return getSwotPoints(document, "div#threat");
    }

    private static List<String> extractWeaknesses(Document document) {
        return getSwotPoints(document, "div#weakness");
    }

    private static List<String> extractOpportunities(Document document) {
        return getSwotPoints(document, "div#opportunity");
    }

    private static List<String> extractStrengths(Document document) {
        return getSwotPoints(document, "div#strength");
    }

    private static List<String> getSwotPoints(Document document, String divSelector) {
        List<String> points = null;
        try {
            Element swotLi = document.select("li#swot_ls").first();
            String url = getSwotUrl(swotLi);
            Document swotDocument = Jsoup.connect(url).get();
            Element swotDiv = swotDocument.select(divSelector).first();

            points = getSwotPoints(swotDiv);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
        return points;
    }

    private static List<String> getSwotPoints(Element swotDiv) {
        List<String> points = new ArrayList<>();
        Element swotUl = swotDiv.select("ul.swotfeatlist").first();
        if(swotUl!=null){
            Elements lis = swotUl.select("li");
            points = lis.stream().map(liNode->liNode.childNode(0).attr("text")).collect(Collectors.toList());
        }

        return points;
    }

    private static String getSwotUrl(Element swotLi) {
        Element swotAnchor = swotLi.select("a").first();
        String onclick = swotAnchor.attr("onclick");
        int httpsIndex =onclick.indexOf("https");
        int nextQuote = onclick.indexOf("'",httpsIndex);
        String url = onclick.substring(httpsIndex,nextQuote);
        return url;
    }


}
