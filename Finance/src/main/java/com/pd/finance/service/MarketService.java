package com.pd.finance.service;

import com.pd.finance.config.ApplicationConfig;
import com.pd.finance.htmlscrapper.marketgainer.MarketGainerEquityFactory;
import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityPerformance;
import com.pd.finance.model.EquitySwotDetails;
import com.pd.finance.request.MarketGainersRequest;
import com.pd.finance.request.PerformanceFilter;
import com.pd.finance.request.SwotFilter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class MarketService implements IMarket {
   private static  Logger logger = LoggerFactory.getLogger(MarketService.class);


   @Autowired
   ApplicationConfig config;

    @Override
    public List<Equity> GetGainers(MarketGainersRequest request) throws Exception{


        try{
            Document doc = getDocument();
            List<Equity> equityCollector = MarketGainerEquityFactory.fetchMarketGainerEquities(doc);

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

    private Document getDocument() throws IOException {
        String gainersUrl = config.getEnvProperty("GainersUrl");
        return Jsoup.connect(gainersUrl).get();
    }


    private boolean isValidEquity(Equity equity, MarketGainersRequest request) {
        boolean isValid = true;
        try {
            List<EquityPerformance> performances = equity.getPerformances().getPerformances();
            PerformanceFilter performanceFilter = request.getPerformanceFilter();
            if(performanceFilter!=null){
                isValid = isValid && isValidEquity(equity, performanceFilter);
            }
            SwotFilter swotFilter = request.getSwotFilter();
            if(swotFilter!=null){
                isValid = isValid && isValidEquity(equity, swotFilter);
            }
            // BigDecimal minimumGainPerSession = performanceFilter.getMinimumGainPerSession()!=null? performanceFilter.getMinimumGainPerSession(): new BigDecimal("0.0");

            return isValid;
        } catch (Exception e) {
           logger.error(e.getMessage(),e);
           return false;
        }

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
