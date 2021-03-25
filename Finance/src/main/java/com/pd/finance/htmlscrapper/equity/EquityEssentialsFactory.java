package com.pd.finance.htmlscrapper.equity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pd.finance.model.EquityEssentials;
import com.pd.finance.model.EquitySwotDetails;
import com.pd.finance.utils.Constants;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class EquityEssentialsFactory implements IEquityEssentialsFactory {

    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(EquityEssentialsFactory.class);


    public static void main(String[] args) throws Exception{
        EquityEssentialsFactory factory = new EquityEssentialsFactory();
        Document document = Jsoup.parse(new File("C:\\Users\\prjen\\Desktop\\Template.html"), "UTF-8");
        EquityEssentials equityEssentials = factory.create(document);
        System.out.println("done");
    }

    @Override
    public   EquityEssentials create(Document document){

        EquityEssentials equityEssentials = new EquityEssentials();

        document.select("div#mces_desk").first().attr("style","display: block;");
        equityEssentials.setEssentials(extractEssentials(document));
        equityEssentials.setFinancial(extractFinancial(document));
        equityEssentials.setIndustryComparison(extractIndustryComparison(document));
        equityEssentials.setOwnerships(extractOwnerships(document));
        equityEssentials.setOthers(extractOthers(document));

        equityEssentials.setSource(Constants.SOURCE_MONEY_CONTROL);
        equityEssentials.setUpdatedDate(new Date());
        return equityEssentials;
    }

    private   String extractEssentials(Document document) {
        String essentials = null;
        try {
            Element essentialsDiv = document.select("div#mcessential_div > div > div > div").first();
            essentials = essentialsDiv.text();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return  essentials;
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
            String question = li.text();
            Element firstPath = li.select("path").first();
            boolean answer = firstPath.attr("style").contains("#3BB54A");
            questionsCollector.put(question,answer);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }



}
