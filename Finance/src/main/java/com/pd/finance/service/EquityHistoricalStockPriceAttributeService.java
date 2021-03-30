package com.pd.finance.service;

import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityHistoricalData;
import com.pd.finance.model.EquityIdentifier;
import com.pd.finance.response.EquityStockExchangeDetailsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Period;
import java.time.chrono.ChronoPeriod;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;

@Service
public class EquityHistoricalStockPriceAttributeService extends HttpGatewayEquityAttributeService   {

    private static final Logger logger = LoggerFactory.getLogger(EquityHistoricalStockPriceAttributeService.class);

    @Autowired
    private IHistoricalStockPriceService historicalStockPriceService;

    @Override
    public void enrichEquity(EquityIdentifier identifier, Equity equity) throws ServiceException {
        try {
            logger.info( "enrichEquity exec started for equity:{}",equity.getEquityIdentifiers());

            EquityHistoricalData equityHistoricalData = historicalStockPriceService.getHistoricalStockPrice(identifier, Period.ofDays(20*365));

            equity.setHistoricalData(equityHistoricalData);
            logger.info("enrichEquity exec completed for equity:{}",equity.getEquityIdentifiers());
        } catch (ServiceException e) {
            logger.error( "enrichEquity exec failed for equity:{}",e.getMessage(),e);
            throw new ServiceException(e);
        }
    }
}
