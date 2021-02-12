package com.pd.finance.service;

import com.pd.finance.model.*;
import com.pd.finance.persistence.EquityRepository;
import com.pd.finance.request.*;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class EquitySearchService implements IEquitySearchService {
    private static final Logger logger = LoggerFactory.getLogger(EquitySearchService.class);
    @Autowired
    private EquityRepository equityRepository;

    @Override
    public List<Equity> search(EquitySearchRequest searchRequest){
        List<Equity> equitiesCollector  = new ArrayList<>();

        Pageable  pageRequest = PageRequest.of(0, 200);
        Page<Equity> page = equityRepository.findAll(pageRequest);
        while (!page.isEmpty())
         {
             List<Equity> list = page.filter(equity -> isValidEquity(equity, searchRequest)).toList();
             equitiesCollector.addAll(list);

             page = equityRepository.findAll(pageRequest.next());

        }

        return equitiesCollector;
    }





    private boolean isValidEquity(Equity equity, EquitySearchRequest request) {
        logger.info("isValidEquity exec started for equity {}",equity.getName());
        try {
            OverviewFilter overviewFilter = request.getOverviewFilter();
            if(overviewFilter!=null){
                if(!isValidEquity(equity, overviewFilter)){
                    logger.info("isValidEquity overviewFilter didn't matched for equity {}",equity.getName());
                    return false;
                }
            }
            PerformanceFilter performanceFilter = request.getPerformanceFilter();
            if(performanceFilter!=null){
                if(!isValidEquity(equity, performanceFilter)){
                    logger.info("isValidEquity performanceFilter didn't matched for equity {}",equity.getName());
                    return false;
                }
            }
            SwotFilter swotFilter = request.getSwotFilter();
            if(swotFilter!=null){
                if(!isValidEquity(equity, swotFilter)){
                    logger.info("isValidEquity swotFilter didn't matched for equity {}",equity.getName());
                    return false;
                }
            }
            TechnicalPeriodFilter technicalPeriodFilter = request.getTechnicalPeriodFilter();
            if(technicalPeriodFilter!=null){
                if(!isValid(equity,technicalPeriodFilter)){
                    logger.info("isValidEquity technicalPeriodFilter didn't matched for equity {}",equity.getName());
                    return false;
                }
            }
            EquityInsightFilter insightFilter = request.getInsightFilter();
            if(insightFilter!=null){
                if(!isValid(equity,insightFilter)){
                    logger.info("isValidEquity insightFilter didn't matched for equity {}",equity.getName());
                    return false;
                }
            }

            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return false;
        }

    }

    private boolean isValid(Equity equity, EquityInsightFilter insightFilter) {
        EquityInsights insights = equity.getInsights();
        if(insights==null){
            return false;
        }

        if(!isValid(insightFilter.getFinancialInsightFilter(),insights.getFinancialInsights())){
            logger.info("isValidEquity FinancialInsightFilter didn't matched for equity {}",equity.getName());
            return false;
        }
        if(!isValid(insightFilter.getPriceInsightFilter(),insights.getPriceInsights())){
            logger.info("isValidEquity PriceInsightFilter didn't matched for equity {}",equity.getName());
            return false;
        }
        if(!isValid(insightFilter.getIndustryComparisionFilter(),insights.getIndustryComparisionInsights())){
            logger.info("isValidEquity IndustryComparisionFilter didn't matched for equity {}",equity.getName());
            return false;
        }
        if(!isValid(insightFilter.getShareholdingPatternsFilter(),insights.getShareholdingPatternInsights())){
            logger.info("isValidEquity ShareholdingPatternsFilter didn't matched for equity {}",equity.getName());
            return false;
        }
        return true;
    }

    private boolean isValid(InsightsFilter shareholdingPatternsFilter, ShareholdingPatternInsights shareholdingPatternInsights) {
        if(shareholdingPatternsFilter!=null){
            if(shareholdingPatternInsights==null){
                return false;
            }
            Integer maxBearish = shareholdingPatternsFilter.getMaxBearish();
            Integer minBullish = shareholdingPatternsFilter.getMinBullish();
            List<EquityInsightLineItem> lineItems = shareholdingPatternInsights.getLineItems();

            if (!isValid(maxBearish, minBullish, lineItems)) {
                return false;
            }
        }
        return true;
    }



    private boolean isValid(InsightsFilter industryComparisionFilter, IndustryComparisionInsights industryComparisionInsights) {
        if(industryComparisionFilter!=null){
            if(industryComparisionInsights==null){
                return false;
            }
            Integer maxBearish = industryComparisionFilter.getMaxBearish();
            Integer minBullish = industryComparisionFilter.getMinBullish();
            List<EquityInsightLineItem> lineItems = industryComparisionInsights.getLineItems();

            if (!isValid(maxBearish, minBullish, lineItems)) {
                return false;
            }
        }
        return true;
    }

    private boolean isValid(InsightsFilter priceInsightFilter, PriceInsights priceInsights) {
        if(priceInsightFilter!=null){
            if(priceInsights==null){
                return false;
            }
            Integer maxBearish = priceInsightFilter.getMaxBearish();
            Integer minBullish = priceInsightFilter.getMinBullish();
            List<EquityInsightLineItem> lineItems = priceInsights.getLineItems();

            if (!isValid(maxBearish, minBullish, lineItems)) {
                return false;
            }
        }
        return true;
    }
    private boolean isValid(Integer maxBearish, Integer minBullish, List<EquityInsightLineItem> lineItems) {
        if( (maxBearish!=null || minBullish!=null) && (lineItems ==null)){
            return false;
        }
        if(maxBearish !=null){
            long count = lineItems.stream().filter(lineItem -> lineItem.getMarketSentiments() == StockMarketSentiments.Bearish).count();
            if(count >maxBearish){
                return false;
            }
        }
        if(minBullish!=null ){
            long count = lineItems.stream().filter(lineItem -> lineItem.getMarketSentiments() == StockMarketSentiments.Bullish).count();
            if(count <minBullish){
                return false;
            }
        }
        return true;
    }

    private boolean isValid(FinancialInsightFilter financialInsightFilter, FinancialInsights financialInsights) {
        if(financialInsightFilter!=null){
            if(financialInsights==null){
                return false;
            }
            BigDecimal minNetProfitCagrGrowth = financialInsightFilter.getMinNetProfitCagrGrowth();
            BigDecimal netProfitCagrGrowth = financialInsights.getNetProfitCagrGrowth();
            if (!isValid(minNetProfitCagrGrowth, netProfitCagrGrowth)) return false;

            BigDecimal minOperatingProfitCagrGrowth = financialInsightFilter.getMinOperatingProfitCagrGrowth();
            BigDecimal operatingProfitCagrGrowth = financialInsights.getOperatingProfitCagrGrowth();
            if (!isValid(minOperatingProfitCagrGrowth, operatingProfitCagrGrowth)) return false;

            BigDecimal minRevenueCagrGrowth = financialInsightFilter.getMinRevenueCagrGrowth();
            BigDecimal revenueCagrGrowth = financialInsights.getRevenueCagrGrowth();
            if (!isValid(minRevenueCagrGrowth, revenueCagrGrowth)) return false;

            BigDecimal minPiotroskiScore = financialInsightFilter.getMinPiotroskiScore();
            BigDecimal piotroskiScore = financialInsights.getPiotroskiScore();
            if (!isValid(minPiotroskiScore, piotroskiScore)) return false;


        }
        return true;
    }

    private boolean isValid(BigDecimal minExpectedValue, BigDecimal actualValue) {
        if (minExpectedValue != null) {

            if (actualValue == null) {
                return false;
            }
            if (minExpectedValue.compareTo(actualValue) > 0) {
                return false;
            }
        }
        return true;
    }


    private boolean isValid(Equity equity, TechnicalPeriodFilter technicalPeriodFilter) {


        TechnicalDetails technicalDetails = equity.getTechnicalDetails();
        if(technicalDetails==null){
            return false;
        }

        if (!isValid(technicalPeriodFilter.getDailyTechnicalFilter(), technicalDetails.getDaily())){
            logger.info("isValidEquity DailyTechnicalFilter didn't matched for equity {}",equity.getName());
            return false;
        }
        if (!isValid(technicalPeriodFilter.getWeeklyTechnicalFilter(), technicalDetails.getWeekly())){
            logger.info("isValidEquity WeeklyTechnicalFilter didn't matched for equity {}",equity.getName());
            return false;
        }
        if (!isValid(technicalPeriodFilter.getMonthlyTechnicalFilter(), technicalDetails.getMonthly())){
            logger.info("isValidEquity MonthlyTechnicalFilter didn't matched for equity {}",equity.getName());
            return false;
        }

        return true;
    }

    private boolean isValid(TechnicalFilter technicalFilter, TechnicalAnalysis technicalAnalysis) {
        if(technicalFilter!=null){

            if(technicalAnalysis==null){
                return false;
            }
            TechAnalysisSummary summary = technicalAnalysis.getSummary();
            if(summary==null){
                return false;
            }


            if (!isValid(technicalFilter.getMovingAvgFilter(), summary.getMovingAverages())){
                logger.info("MovingAverages filter not matching returning false");
                return false;
            }

            if (!isValid(technicalFilter.getMovingAvgCrossOverFilter(), summary.getMovingAveragesCrossOver())){
                logger.info("MovingAveragesCrossOver filter not matching returning false");
                return false;
            }

            if (!isValid(technicalFilter.getTechnicalIndicatorFilter(), summary.getTechnicalIndicator())){
                logger.info("TechnicalIndicator filter not matching returning false");
                return false;
            }


        }
        return true;
    }

    private boolean isValid(TechnicalSummaryFilter summaryFilter, TechAnalysisSummaryValue summaryValue) {
        if(summaryFilter!=null){

            if(summaryValue==null){
                return false;
            }
            Integer maxBearishFilter = summaryFilter.getMaxBearish();
            if(maxBearishFilter!=null && maxBearishFilter < summaryValue.getBearish()){
                return false;
            }
            Integer minBullishFilter = summaryFilter.getMinBullish();
            if(minBullishFilter!=null && minBullishFilter > summaryValue.getBullish()){
                return false;
            }

        }
        return true;
    }

    private boolean isValidEquity(Equity equity, OverviewFilter overviewFilter) {
        boolean isValid = false;

        try {
            EquityOverview equityOverview = equity.getOverview();
            isValid =  overviewFilter.getMaxPE().compareTo(equityOverview.getStockPE())>=0 &&
                    overviewFilter.getMinVolume().compareTo(equityOverview.getVolume())<=0

            ;
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return isValid;
    }

    private boolean isValidEquity(Equity equity, SwotFilter swotFilter) {
        boolean isValid = false;

        try {
            EquitySwotDetails swotDetails = equity.getSwotDetails();
            isValid = swotDetails.getStrengths().size()>= swotFilter.getMinStrengths() &&
                    swotDetails.getOpportunities().size()>=swotFilter.getMinOpportunities() &&
                    swotDetails.getWeaknesses().size()<=swotFilter.getMaxWeaknesses() &&
                    swotDetails.getThreats().size()<=swotFilter.getMaxThreats()
            ;
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return isValid;
    }

    private boolean isValidEquity(Equity equity, PerformanceFilter performanceFilter) {
        boolean isValid = false;

        try {
            EquityPerformances performances = equity.getPerformances();
            if (performances!=null) {
                List<EquityPerformance> equityPerformanceList = performances.getPerformances();
                if (equityPerformanceList!=null) {
                    isValid = equityPerformanceList.stream()
                            .filter(performance -> performance.getChangePercent().compareTo(performanceFilter.getMinimumGainPerSession()) >= 0)
                            .collect(Collectors.toList()).size() >= performanceFilter.getMinimumGainSessions();
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return isValid;
    }

}
