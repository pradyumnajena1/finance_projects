package com.pd.finance.htmlscrapper.equity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pd.finance.model.*;
import com.pd.finance.service.IDocumentService;
import com.pd.finance.utils.CommonUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EquityTechnicalDetailsFactory implements IEquityTechnicalDetailsFactory {

    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(EquityTechnicalDetailsFactory.class);
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
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }


        return technicalDetails;
    }

    private TechnicalAnalysis extractTechDetailsMonthly(String technicalDetailsTemplateUrl) {
        return extractTechnicalAnalysisForPeriod(technicalDetailsTemplateUrl, "monthly","div#techratingsumm");
    }

    private TechnicalAnalysis extractTechDetailsWeekly(String technicalDetailsTemplateUrl) {
        return extractTechnicalAnalysisForPeriod(technicalDetailsTemplateUrl, "weekly","div#techratingsumm");
    }

    private TechnicalAnalysis extractTechDetailsDaily(String technicalDetailsTemplateUrl) {
        return extractTechnicalAnalysisForPeriod(technicalDetailsTemplateUrl, "daily","div#techratingsumd");
    }

    private TechnicalAnalysis extractTechnicalAnalysisForPeriod(String technicalDetailsTemplateUrl, String period, String cssQuery) {
        TechnicalAnalysis technicalAnalysis = new TechnicalAnalysis();
        try {
            String technicalDetailsDailyUrl = getTechnicalDetailsUrl(technicalDetailsTemplateUrl, period);
            Document document = documentService.getDocument(technicalDetailsDailyUrl);
            technicalAnalysis.setSummary(extractSummary(document, cssQuery));

            technicalAnalysis.setMovingAverages(extractMovingAverages(document));
            technicalAnalysis.setMovingAveragesCrossovers(extractMovingAvgCrossovers(document));
            technicalAnalysis.setTechnicalIndicator(extractTechnicalIndicator(document));


        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return technicalAnalysis;
    }

    private TechnicalIndicatorDetails extractTechnicalIndicator(Document document) {
        return null;
    }

    private MovingAvgCrossoverDetails extractMovingAvgCrossovers(Document document) {
        return null;
    }

    private MovingAvgDetails extractMovingAverages(Document document) {
        return null;
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
        int value = extractTechnicalSummaryValueValue(techRatingSummaryDiv, rowSelector);
        summaryValue.setValue(value);
        return summaryValue;
    }

    private int extractTechnicalSummaryValueValue(Element techRatingSummaryDiv,String rowSelector) {
        Element tbody = techRatingSummaryDiv.select("tbody").first();
        Element tr = tbody.select(rowSelector).first();
        Element valueTd = tr.select("td:eq(2)").first();
        Element strongElement = valueTd.select("div:eq(0)").first().select("span:eq(0)").first().select("strong:eq(0)").first();
        return CommonUtils.extractIntegerFromText(strongElement.text()).intValue();
    }

    private String getTechnicalDetailsUrl(String technicalDetailsTemplateUrl, String period) {
        int lastIndexOf = technicalDetailsTemplateUrl.lastIndexOf("/");
        return technicalDetailsTemplateUrl.substring(0,lastIndexOf+1)+period;
    }
}
