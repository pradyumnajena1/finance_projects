package com.pd.finance.service;

import com.pd.finance.config.ApplicationConfig;
import com.pd.finance.htmlscrapper.marketgainer.IMarketGainerEquityFactory;
import com.pd.finance.htmlscrapper.marketgainer.MarketGainerEquityFactory;
import com.pd.finance.model.*;
import com.pd.finance.request.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class MarketService implements IMarketService {
   private static final Logger logger = LoggerFactory.getLogger(MarketService.class);
   @Autowired
   private IMarketGainerEquityFactory marketGainerEquityFactory;

   @Autowired
   private ApplicationConfig config;
   @Autowired
   private IDocumentService documentService;


    @Override
    public List<Equity> GetGainers(MarketGainersRequest request) throws Exception{


        try{
            Document doc = getDocument();
            List<Equity> equityCollector = marketGainerEquityFactory.fetchMarketGainerEquities(doc,request.getDebugFilter()!=null?request.getDebugFilter().getNumEquities():-1);

            List<Equity> result = filterEquities(request, equityCollector);

            return result;
        }catch (Exception ex){
            logger.error("Failed to execute GetGainers",ex);
               throw ex;
        }




    }

    private List<Equity> filterEquities(MarketGainersRequest request, List<Equity> equityCollector) {
        return equityCollector.stream().filter(anEquity->isValidEquity(anEquity,request)).collect(Collectors.toList());
    }

    private Document getDocument() throws Exception {
        String gainersUrl = config.getEnvProperty("GainersUrl");
        return documentService.getDocument(gainersUrl);
    }


    private boolean isValidEquity(Equity equity, MarketGainersRequest request) {
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

            return true;
        } catch (Exception e) {
           logger.error(e.getMessage(),e);
           return false;
        }

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
            isValid = equity.getPerformances().getPerformances().stream()
                  .filter(performance -> performance.getChangePercent().compareTo(performanceFilter.getMinimumGainPerSession()) >= 0)
                  .collect(Collectors.toList()).size() == performanceFilter.getMinimumGainSessions();
        } catch (Exception e) {
           logger.error(e.getMessage(),e);
        }
        return isValid;
    }


}
