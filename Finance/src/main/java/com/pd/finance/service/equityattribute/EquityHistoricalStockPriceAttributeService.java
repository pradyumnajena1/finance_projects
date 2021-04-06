package com.pd.finance.service.equityattribute;

import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityHistoricalData;
import com.pd.finance.model.EquityHistoricalIntervalData;
import com.pd.finance.model.EquityIdentifier;
import com.pd.finance.service.HistoricalDataInterval;
import com.pd.finance.service.IYahooService;
import com.pd.finance.utils.Constants;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Period;
import java.util.Date;

@Service
public class EquityHistoricalStockPriceAttributeService extends HttpGatewayEquityAttributeService {

    private static final Logger logger = LoggerFactory.getLogger(EquityHistoricalStockPriceAttributeService.class);

    @Autowired
    private IYahooService yahooService;

    @Override
    public void enrichEquity(EquityIdentifier identifier, Equity equity) throws ServiceException {
        try {
            logger.info( "enrichEquity exec started for equity:{}",equity.getDefaultEquityIdentifier());
            EquityHistoricalData historicalData = new EquityHistoricalData();

            EquityHistoricalIntervalData dailyHistoricalIntervalData = getEquityHistoricalIntervalData(identifier, HistoricalDataInterval.OneDay);
            historicalData.setDailyHistoricalData(dailyHistoricalIntervalData);

            EquityHistoricalIntervalData weeklyHistoricalIntervalData = getEquityHistoricalIntervalData(identifier, HistoricalDataInterval.OneWeek);
            historicalData.setWeeklyHistoricalIntervalData(weeklyHistoricalIntervalData);

            EquityHistoricalIntervalData monthlyHistoricalIntervalData = getEquityHistoricalIntervalData(identifier, HistoricalDataInterval.OneMonth);
            historicalData.setMonthlyEquityHistoricalIntervalData(monthlyHistoricalIntervalData);


            historicalData.setSource(Constants.SOURCE_YAHOO_FINANCE);
            historicalData.setUpdatedDate(new Date());
            equity.setHistoricalData(historicalData);
            logger.info("enrichEquity exec completed for equity:{}",equity.getDefaultEquityIdentifier());
        } catch (Exception e) {
            logger.error( "enrichEquity exec failed for equity:{}",e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    @NotNull
    protected EquityHistoricalIntervalData getEquityHistoricalIntervalData(EquityIdentifier identifier, HistoricalDataInterval oneDay) throws ServiceException {
        EquityHistoricalIntervalData dailyHistoricalIntervalData = yahooService.getHistoricalStockPrice(identifier, Period.ofDays(20 * 365), oneDay);
        dailyHistoricalIntervalData.setSource(Constants.SOURCE_YAHOO_FINANCE);
        dailyHistoricalIntervalData.setUpdatedDate(new Date());
        return dailyHistoricalIntervalData;
    }
}
