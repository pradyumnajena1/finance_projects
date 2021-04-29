package com.pd.finance.htmlscrapper.equity;

import com.pd.finance.model.*;
import com.pd.finance.utils.CommonUtils;
import com.pd.finance.utils.Constants;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

@Component
public class EquityInsightsFactory implements IEquityInsightsFactory {

    private static final Logger logger = LoggerFactory.getLogger(EquityInsightsFactory.class);

    public static void main(String[] args) throws Exception {
        EquityInsightsFactory factory = new EquityInsightsFactory();
        Document document = Jsoup.parse(new File("C:\\Users\\prjen\\Desktop\\Template.html"), "UTF-8");
        EquityInsights equityInsights = factory.create(document);
        System.out.println("done");
    }

    @Override
    public EquityInsights create(Document document) {
        EquityInsights equityInsights = new EquityInsights();
        equityInsights.setInsightHeadline(extractInsightHeadline(document));
        equityInsights.setFinancialInsights(extractFinancialInsights(document));
        equityInsights.setIndustryComparisionInsights(extractIndustryComparisionInsights(document));
        equityInsights.setPriceInsights(extractPriceInsights(document));
        equityInsights.setShareholdingPatternInsights(extractShareholdingPatternInsights(document));

        equityInsights.setSource(Constants.SOURCE_MONEY_CONTROL);
        equityInsights.setUpdatedDate(new Date());

        return equityInsights;
    }

    private EquityInsightLineItem extractInsightHeadline(Document document) {


        EquityInsightLineItem lineItem = new EquityInsightLineItem();
        Element first = document.select("div#mc_insight > div.mcpperf.grey_bx > p:nth-child(1)").first();
        lineItem.setInsight(first.text());
        String pclass = first.attr("class");
        StockMarketSentiments sms = getSms(pclass);
        lineItem.setMarketSentiments(sms);
        return lineItem;
    }

    private StockMarketSentiments getSms(String attrValue) {
        if (attrValue == null) {
            attrValue = "";
        }
        if (attrValue.contains("green")) {
            return StockMarketSentiments.Bearish;
        } else if (attrValue.contains("red")) {
            return StockMarketSentiments.Bullish;
        } else if (attrValue.contains("neutral")) {
            return StockMarketSentiments.Neutral;
        }

        return StockMarketSentiments.Neutral;


    }


    private PriceInsights extractPriceInsights(Document document) {
        PriceInsights priceInsights = new PriceInsights();
        ArrayList<EquityInsightLineItem> lineItems = new ArrayList<>();
        priceInsights.setLineItems(lineItems);
        document.select("div#mc_insight > div.clearfix.mcibx_cnt > div:nth-child(1) > ul > li").stream().forEach(li -> {

            EquityInsightLineItem equityInsightLineItem = new EquityInsightLineItem();
            equityInsightLineItem.setInsight(li.text());
            equityInsightLineItem.setMarketSentiments(getSms(li.attr("class")));
            lineItems.add(equityInsightLineItem);

        });
        return priceInsights;
    }

    private FinancialInsights extractFinancialInsights(Document document) {
        FinancialInsights financialInsights = new FinancialInsights();

        String baseSelector = "div#mc_insight > div.clearfix.mcibx_cnt > div:nth-child(2)";
        String piotroskiValueText = document.select(baseSelector + " > div.fpioi > div.nof").first().text();
        financialInsights.setPiotroskiScore(CommonUtils.extractDecimalFromText(piotroskiValueText));

        String tableBodySelector = " > table > tbody";
        Element first = document.select(baseSelector + tableBodySelector + " > tr:nth-child(2) > td:nth-child(2)").first();
        if (first != null) {
            String netprofitText = first.text();
            financialInsights.setNetProfitCagrGrowth(CommonUtils.extractDecimalFromText(netprofitText));
        }

        first = document.select(baseSelector + tableBodySelector + " > tr:nth-child(3) > td:nth-child(2)").first();
        if (first != null) {
            String operatingProfitText = first.text();
            financialInsights.setOperatingProfitCagrGrowth(CommonUtils.extractDecimalFromText(operatingProfitText));
        }
        first = document.select(baseSelector + tableBodySelector + " > tr:nth-child(1) > td:nth-child(2)").first();
        if (first != null) {
            String revenueText = first.text();
            financialInsights.setRevenueCagrGrowth(CommonUtils.extractDecimalFromText(revenueText));
        }

        return financialInsights;
    }

    private IndustryComparisionInsights extractIndustryComparisionInsights(Document document) {
        IndustryComparisionInsights industryComparisionInsights = new IndustryComparisionInsights();
        ArrayList<EquityInsightLineItem> lineItems = new ArrayList<>();
        industryComparisionInsights.setLineItems(lineItems);

        document.select("div#mc_insight > div.clearfix.mcibx_cnt > div:nth-child(3) > ul > li").stream().forEach(li -> {
            EquityInsightLineItem equityInsightLineItem = new EquityInsightLineItem();
            equityInsightLineItem.setInsight(li.text());
            equityInsightLineItem.setMarketSentiments(getSms(li.attr("class")));
            lineItems.add(equityInsightLineItem);
        });
        return industryComparisionInsights;
    }


    private ShareholdingPatternInsights extractShareholdingPatternInsights(Document document) {
        ShareholdingPatternInsights shareholdingPatternInsights = new ShareholdingPatternInsights();
        ArrayList<EquityInsightLineItem> lineItems = new ArrayList<>();
        shareholdingPatternInsights.setLineItems(lineItems);

        document.select("#mc_insight > div.clearfix.mcibx_cnt > div:nth-child(4) > ul > li").stream().forEach(li -> {
            EquityInsightLineItem lineItem = new EquityInsightLineItem();
            lineItem.setInsight(li.text());
            lineItem.setMarketSentiments(getSms(li.attr("class")));
            lineItems.add(lineItem);

        });
        return shareholdingPatternInsights;
    }


}
