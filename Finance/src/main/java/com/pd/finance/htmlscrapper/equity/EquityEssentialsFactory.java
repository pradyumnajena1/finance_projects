package com.pd.finance.htmlscrapper.equity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pd.finance.model.EquityEssentials;
import com.pd.finance.model.EquitySwotDetails;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class EquityEssentialsFactory {

    @JsonIgnore
    private static Logger logger = LoggerFactory.getLogger(EquityEssentialsFactory.class);


    public static EquityEssentials create(Document document){

        EquityEssentials equityEssentials = new EquityEssentials();

        document.select("div#mces_desk").first().attr("style","display: block;");
        equityEssentials.setEssentials(extractEssentials(document));
        equityEssentials.setFinancial(extractFinancial(document));
        equityEssentials.setIndustryComparison(extractIndustryComparison(document));
        equityEssentials.setOwnerships(extractOwnerships(document));
        equityEssentials.setOthers(extractOthers(document));


        return equityEssentials;
    }

    private static Map<String, Boolean> extractOthers(Document document) {
        return collectQuestionsForSegment(document, "ul#id_others");
    }

    private static Map<String, Boolean> extractOwnerships(Document document) {
        return collectQuestionsForSegment(document, "ul#id_ownership");
    }
    private static Map<String, Boolean> extractFinancial(Document document) {
        return collectQuestionsForSegment(document, "ul#id_financials");
    }
    private static Map<String, Boolean> extractIndustryComparison(Document document) {
        return collectQuestionsForSegment(document, "ul#id_induscmp");
    }

    private static Map<String, Boolean> collectQuestionsForSegment(Document document, String selectorQuery) {
        Map<String, Boolean> questionsCollector = new HashMap<>();
        try {
            Element financialUl = document.select(selectorQuery).first();
            financialUl.select("li").stream().forEach(li -> addQuestionAndAnswer(questionsCollector, li));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return questionsCollector;
    }





    private static void addQuestionAndAnswer(Map<String, Boolean> questionsCollector, Element li) {
        try {
            String question = li.child(0).attr("text");
            Element firstPath = li.select("path").first();
            boolean answer = firstPath.attr("fill").equalsIgnoreCase("");
            questionsCollector.put(question,answer);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }


    private static String extractEssentials(Document document) {
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
