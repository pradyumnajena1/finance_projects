package com.pd.finance.htmlscrapper.equity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pd.finance.model.*;
import com.pd.finance.service.IDocumentService;
import com.pd.finance.utils.CommonUtils;
import com.pd.finance.utils.Constants;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Period;
import java.util.ArrayList;
import java.util.Date;

@Component
public class EquityTechnicalDetailsFactory implements IEquityTechnicalDetailsFactory {

    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(EquityTechnicalDetailsFactory.class);
    public static final int NUM_DAYS_TO_CACHE = 5;
    @Autowired
    private IDocumentService documentService;

    @Override
    public TechnicalDetails create(Document document){
        TechnicalDetails technicalDetails = new TechnicalDetails();

        try {
            Element technicalDiv = document.select("div:containsOwn(MC Technicals)").first();

            String technicalDetailsUrl = technicalDiv.parent().attr("href");
            technicalDetails.setDaily(extractTechDetailsDaily(technicalDetailsUrl));
            technicalDetails.setWeekly(extractTechDetailsWeekly(technicalDetailsUrl));
            technicalDetails.setMonthly(extractTechDetailsMonthly(technicalDetailsUrl));

            technicalDetails.setSource(Constants.SOURCE_MONEY_CONTROL);
            technicalDetails.setUpdatedDate(new Date());
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }


        return technicalDetails;
    }

    private TechnicalAnalysis extractTechDetailsMonthly(String technicalDetailsTemplateUrl) {
        TechnicalAnalysisPeriodCssSelectors cssSelectors = new TechnicalAnalysisPeriodCssSelectors("monthly", "div#techrs_m", "div#movavg_m", "div#movavgcr_m", "div#techind_m");
        return extractTechnicalAnalysisForPeriod(technicalDetailsTemplateUrl, cssSelectors);
    }

    private TechnicalAnalysis extractTechDetailsWeekly(String technicalDetailsTemplateUrl) {
        TechnicalAnalysisPeriodCssSelectors cssSelectors = new TechnicalAnalysisPeriodCssSelectors("weekly", "div#techratingsumm", "div#movingavg", "div#movavgco", "div#techind");
        return extractTechnicalAnalysisForPeriod(technicalDetailsTemplateUrl, cssSelectors);
    }

    private TechnicalAnalysis extractTechDetailsDaily(String technicalDetailsTemplateUrl) {
        TechnicalAnalysisPeriodCssSelectors cssSelectors = new TechnicalAnalysisPeriodCssSelectors("daily", "div#techratingsumd", "div#movingavgd", "div#movavgcod", "div#techindd");
        return extractTechnicalAnalysisForPeriod(technicalDetailsTemplateUrl, cssSelectors);
    }

    private TechnicalAnalysis extractTechnicalAnalysisForPeriod(String technicalDetailsTemplateUrl, TechnicalAnalysisPeriodCssSelectors technicalAnalysisPeriodCssSelectors) {
        TechnicalAnalysis technicalAnalysis = new TechnicalAnalysis();
        try {
            String technicalDetailsPeriodUrl = getTechnicalDetailsUrl(technicalDetailsTemplateUrl, technicalAnalysisPeriodCssSelectors.getPeriod());
            Document document = documentService.getDocument(technicalDetailsPeriodUrl, Period.ofDays(NUM_DAYS_TO_CACHE));
            technicalAnalysis.setSummary(extractSummary(document, technicalAnalysisPeriodCssSelectors.getSummaryCssQuery()));

            technicalAnalysis.setMovingAverages(extractMovingAverages(document, technicalAnalysisPeriodCssSelectors.getMovingAvgCssQuery()));
            technicalAnalysis.setMovingAveragesCrossovers(extractMovingAvgCrossovers(document, technicalAnalysisPeriodCssSelectors.getMovingAvgCrossoverCssQuery()));
            technicalAnalysis.setTechnicalIndicator(extractTechnicalIndicator(document, technicalAnalysisPeriodCssSelectors.getTechnicalIndicatorCssQuery()));


        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return technicalAnalysis;
    }

    private TechnicalIndicatorDetails extractTechnicalIndicator(Document document, String technicalIndicatorCssQuery) {
        TechnicalIndicatorDetails technicalIndicatorDetails = new TechnicalIndicatorDetails();
        ArrayList<TechIndicatorLineItem> lineItems = new ArrayList<>();
        technicalIndicatorDetails.setLineItems(lineItems);
        Element containerDiv = document.select(technicalIndicatorCssQuery).first();

        Elements elements = containerDiv.select("tbody > tr");
        elements.stream().forEach(aRow->{
            TechIndicatorLineItem techIndicatorLineItem = extractTechIndicatorLineItem(aRow);
            lineItems.add(techIndicatorLineItem);

        });
        return technicalIndicatorDetails;
    }

    private TechIndicatorLineItem extractTechIndicatorLineItem(Element aRow) {
        TechIndicatorLineItem techIndicatorLineItem = new TechIndicatorLineItem();
        try {
            techIndicatorLineItem.setIndicator(aRow.select("td:eq(0)").first().text());
            techIndicatorLineItem.setLevel(aRow.select("td:eq(1)").first().text());
            techIndicatorLineItem.setIndication(aRow.select("td:eq(2)").first().text());
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return techIndicatorLineItem;
    }

    private MovingAvgCrossoverDetails extractMovingAvgCrossovers(Document document, String movingAvgCrossoverCssQuery) {
        MovingAvgCrossoverDetails crossoverDetails = new MovingAvgCrossoverDetails();
        ArrayList<MovingAvgCrossoverLineItem> lineItems = new ArrayList<>();
        crossoverDetails.setLineItems(lineItems);
        Element containerDiv = document.select(movingAvgCrossoverCssQuery).first();
        Elements elements = containerDiv.select("tbody > tr");
        elements.stream().forEach(aRow->{
            MovingAvgCrossoverLineItem crossoverLineItem = extractMovingAvgCrossoverLineItem(aRow);
            lineItems.add(crossoverLineItem);

        });
        return crossoverDetails;
    }

    private MovingAvgCrossoverLineItem extractMovingAvgCrossoverLineItem(Element aRow) {
        MovingAvgCrossoverLineItem crossoverLineItem = new MovingAvgCrossoverLineItem();
        try {
            crossoverLineItem.setPeriod(  aRow.select("td:eq(0)").first().text() );
            crossoverLineItem.setMovingAvgCrossover(aRow.select("td:eq(1)").first().text());
            crossoverLineItem.setIndication(aRow.select("td:eq(2)").first().text());
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return crossoverLineItem;
    }

    private MovingAvgDetails extractMovingAverages(Document document, String movingAvgCssQuery) {
        MovingAvgDetails movingAvgDetails = new MovingAvgDetails();
        ArrayList<MovingAverageLineItem> lineItems = new ArrayList<>();
        movingAvgDetails.setLineItems(lineItems);
        Element containerDiv = document.select(movingAvgCssQuery).first();
        Elements elements = containerDiv.select("tbody > tr");
        elements.stream().forEach(aRow->{
            MovingAverageLineItem movingAverageLineItem = extractMovingAverageLineItem(aRow);
            lineItems.add(movingAverageLineItem);

        });
        return movingAvgDetails;
    }

    private MovingAverageLineItem extractMovingAverageLineItem(Element aRow) {
        MovingAverageLineItem movingAverageLineItem = new MovingAverageLineItem();

        try {
            movingAverageLineItem.setIndication(aRow.select("td:eq(2)").first().text());
            movingAverageLineItem.setPeriod(CommonUtils.extractIntegerFromText( aRow.select("td:eq(0)").first().text()).intValue());
            movingAverageLineItem.setSMA(CommonUtils.extractDecimalFromText( aRow.select("td:eq(1)").first().text()));
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }

        return movingAverageLineItem;
    }

    private TechAnalysisSummary extractSummary(Document document, String cssQuery) {
        TechAnalysisSummary techAnalysisSummary = new TechAnalysisSummary();
        try {
            Element techRatingSummaryDiv = document.select(cssQuery).first();

            techAnalysisSummary.setMovingAverages(extractMovingAveragesSummaryValue(techRatingSummaryDiv));
            techAnalysisSummary.setMovingAveragesCrossOver(extractMovingAverageCrossoversSummaryValue(techRatingSummaryDiv));
            techAnalysisSummary.setTechnicalIndicator(extractTechnicalIndicatorSummaryValue(techRatingSummaryDiv));
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }

        return techAnalysisSummary;
    }

    private TechAnalysisSummaryValue extractTechnicalIndicatorSummaryValue(Element techRatingSummaryDiv) {
        return extractTechnicalSummaryLineItem(techRatingSummaryDiv, "Technical Indicator", "tr:eq(1)");
    }

    private TechAnalysisSummaryValue extractMovingAverageCrossoversSummaryValue(Element techRatingSummaryDiv) {
        return extractTechnicalSummaryLineItem(techRatingSummaryDiv, "Moving Averages Crossovers", "tr:eq(2)");
    }

    private TechAnalysisSummaryValue extractMovingAveragesSummaryValue(Element techRatingSummaryDiv) {
        return extractTechnicalSummaryLineItem(techRatingSummaryDiv, "Moving Averages", "tr:eq(0)");
    }

    private TechAnalysisSummaryValue extractTechnicalSummaryLineItem(Element techRatingSummaryDiv, String trend, String rowSelector) {
        TechAnalysisSummaryValue summaryValue = new TechAnalysisSummaryValue();
        summaryValue.setTrend(trend);
        String value = extractTechnicalSummaryValueValue(techRatingSummaryDiv, rowSelector);
        summaryValue.setValue(value);
        return summaryValue;
    }

    private String extractTechnicalSummaryValueValue(Element techRatingSummaryDiv,String rowSelector) {
        String value = null;
        try {
            Element tbody = techRatingSummaryDiv.select("tbody").first();
            Element tr = tbody.select(rowSelector).first();
            Element valueTd = tr.select("td:eq(2)").first();
            value=  valueTd.select("div:eq(0)").first().text();
        } catch (Exception e) {
           logger.error(e.getMessage(),e);
        }
        return value;
    }

    private String getTechnicalDetailsUrl(String technicalDetailsTemplateUrl, String period) {
        int lastIndexOf = technicalDetailsTemplateUrl.lastIndexOf("/");
        return technicalDetailsTemplateUrl.substring(0,lastIndexOf+1)+period;
    }
}
