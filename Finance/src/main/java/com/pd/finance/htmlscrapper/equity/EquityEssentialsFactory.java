package com.pd.finance.htmlscrapper.equity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pd.finance.model.EquityEssentials;
import com.pd.finance.model.EquitySwotDetails;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EquityEssentialsFactory implements IEquityEssentialsFactory {

    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(EquityEssentialsFactory.class);


    @Override
    public   EquityEssentials create(Document document){

        EquityEssentials equityEssentials = new EquityEssentials();

        document.select("div#mces_desk").first().attr("style","display: block;");
        equityEssentials.setEssentials(extractEssentials(document));
        equityEssentials.setFinancial(extractFinancial(document));
        equityEssentials.setIndustryComparison(extractIndustryComparison(document));
        equityEssentials.setOwnerships(extractOwnerships(document));
        equityEssentials.setOthers(extractOthers(document));


        return equityEssentials;
    }

    private   Map<String, Boolean> extractOthers(Document document) {
        return collectQuestionsForSegment(document, "ul#id_others");
    }

    private   Map<String, Boolean> extractOwnerships(Document document) {
        return collectQuestionsForSegment(document, "ul#id_ownership");
    }
    private   Map<String, Boolean> extractFinancial(Document document) {
        return collectQuestionsForSegment(document, "ul#id_financials");
    }
    private   Map<String, Boolean> extractIndustryComparison(Document document) {
        return collectQuestionsForSegment(document, "ul#id_induscmp");
    }

    private   Map<String, Boolean> collectQuestionsForSegment(Document document, String selectorQuery) {
        Map<String, Boolean> questionsCollector = new HashMap<>();
        try {
            Element financialUl = document.select(selectorQuery).first();
            financialUl.select("li").stream().forEach(li -> addQuestionAndAnswer(questionsCollector, li));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return questionsCollector;
    }





    private   void addQuestionAndAnswer(Map<String, Boolean> questionsCollector, Element li) {
        try {
            String question = li.child(0).attr("text");
            Element firstPath = li.select("path").first();
            boolean answer = firstPath.attr("fill").equalsIgnoreCase("");
            questionsCollector.put(question,answer);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }


    private   String extractEssentials(Document document) {
        String essentials = null;
        try {
            Element essentialsDiv = document.select("div#mcessential_div").first();
            Element first = essentialsDiv.select("div:containsOwn( Pass)").first();

            essentials = first.childNode(0).attr("text");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return  essentials;
    }
}
