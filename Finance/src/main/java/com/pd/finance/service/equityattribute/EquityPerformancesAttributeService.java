package com.pd.finance.service.equityattribute;

import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.model.*;
import com.pd.finance.service.HistoricalDataInterval;
import com.pd.finance.service.IYahooService;
import com.pd.finance.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class EquityPerformancesAttributeService extends HttpGatewayEquityAttributeService{

    private static final Logger logger = LoggerFactory.getLogger(EquityPerformancesAttributeService.class);

    @Autowired
    private IYahooService yahooService;

    @Override
    public void enrichEquity(EquityIdentifier identifier, Equity equity) throws ServiceException {
        try {
            logger.info( "enrichEquity exec started for equity:{}",equity.getDefaultEquityIdentifier());

            EquityHistoricalIntervalData equityHistoricalIntervalData = yahooService.getHistoricalStockPrice(identifier, Period.ofDays(20), HistoricalDataInterval.OneDay);
            if (equityHistoricalIntervalData!=null) {
                List<EquityPerformance> performances = new ArrayList<>();
                List<EquityHistoricalDataLineItem> lineItems = equityHistoricalIntervalData.getLineItems();
                Collections.sort(lineItems);

                int startIndex = Math.max(lineItems.size() - 5, 0);
                int endIndex = lineItems.size() - 1;

                for(int i = startIndex; i<= endIndex; i++){

                    EquityHistoricalDataLineItem currentLineItem = lineItems.get(i);
                    EquityHistoricalDataLineItem previousLineItem = null;
                    if(i>0){

                          previousLineItem = lineItems.get(i - 1);
                    }

                    EquityPerformance performance = getPerformance(currentLineItem, previousLineItem);

                    performances.add(performance);
                }
                EquityPerformances equityPerformances =new EquityPerformances("Last 5 day performance", performances );
                equityPerformances.setSource(Constants.SOURCE_YAHOO_FINANCE);
                equityPerformances.setUpdatedDate(new Date());

                equity.setPerformances(equityPerformances);
            }

            logger.info("enrichEquity exec completed for equity:{}",equity.getDefaultEquityIdentifier());
        } catch (Exception e) {
            logger.error( "enrichEquity exec failed for equity:{}",e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    private EquityPerformance getPerformance( EquityHistoricalDataLineItem currentLineItem,EquityHistoricalDataLineItem previousLineItem) {
        EquityPerformance performance = new EquityPerformance();
        performance.setDate(currentLineItem.getDate());
        performance.setPrice(currentLineItem.getClose());

        BigDecimal change = getChange(currentLineItem,previousLineItem);
        performance.setChange(change);
        BigDecimal prevClose = BigDecimal.ZERO;
         if(previousLineItem!=null){

               prevClose = previousLineItem.getClose();
         }
        BigDecimal changePercent = null;

        if(change!=null && !BigDecimal.ZERO.equals( prevClose)){

              changePercent = performance.getChange().divide(prevClose, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100.00));
        }
        performance.setChangePercent(changePercent);
        return performance;
    }

    private BigDecimal getChange(EquityHistoricalDataLineItem currentLineItem, EquityHistoricalDataLineItem previousLineItem) {
        BigDecimal change = null;
        BigDecimal close = currentLineItem.getClose();
        BigDecimal prevClose = BigDecimal.ZERO;
        if(previousLineItem!=null){

              prevClose = previousLineItem.getOpen();
        }
        if(prevClose!=null && close!=null){
           change = close.subtract(prevClose);
        }
        return change;
    }
}
