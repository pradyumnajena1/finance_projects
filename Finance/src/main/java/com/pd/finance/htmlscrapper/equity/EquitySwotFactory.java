package com.pd.finance.htmlscrapper.equity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pd.finance.model.EquitySwotDetails;
import com.pd.finance.service.IDocumentService;
import com.pd.finance.utils.Constants;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class EquitySwotFactory implements IEquitySwotFactory {


    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(EquitySwotFactory.class);
    @Autowired
    private IDocumentService documentService;
    @Override
    public   EquitySwotDetails create(Document document){

        EquitySwotDetails swotDetails = new EquitySwotDetails();


        swotDetails.setStrengths(extractStrengths(document));
        swotDetails.setOpportunities(extractOpportunities(document));
        swotDetails.setWeaknesses(extractWeaknesses(document));
        swotDetails.setThreats(extractThreats(document));

        swotDetails.setSource(Constants.SOURCE_MONEY_CONTROL);
        swotDetails.setUpdatedDate(new Date());

        return swotDetails;
    }





    private   List<String> extractThreats(Document document) {
        return getSwotPoints(document, "div#threat");
    }

    private   List<String> extractWeaknesses(Document document) {
        return getSwotPoints(document, "div#weakness");
    }

    private   List<String> extractOpportunities(Document document) {
        return getSwotPoints(document, "div#opportunity");
    }

    private   List<String> extractStrengths(Document document) {
        return getSwotPoints(document, "div#strength");
    }

    private   List<String> getSwotPoints(Document document, String divSelector) {
        List<String> points = null;
        try {
            Element swotLi = document.select("li#swot_ls").first();
            String url = getSwotUrl(swotLi);
            Document swotDocument = documentService.getDocument(url, Period.ofDays(1));
            Element swotDiv = swotDocument.select(divSelector).first();

            points = getSwotPoints(swotDiv);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return points;
    }

    private   List<String> getSwotPoints(Element swotDiv) {
        List<String> points = new ArrayList<>();
        Element swotUl = swotDiv.select("ul.swotfeatlist").first();
        if(swotUl!=null){
            Elements lis = swotUl.select("li");
            points = lis.stream().map(liNode->liNode.childNode(0).attr("text")).collect(Collectors.toList());
        }

        return points;
    }

    private   String getSwotUrl(Element swotLi) {
        Element swotAnchor = swotLi.select("a").first();
        String onclick = swotAnchor.attr("onclick");
        int httpsIndex =onclick.indexOf("https");
        int nextQuote = onclick.indexOf("'",httpsIndex);
        String url = onclick.substring(httpsIndex,nextQuote);
        return url;
    }


}
