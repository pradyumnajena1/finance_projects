package com.pd.finance.service;

import com.pd.finance.config.ApplicationConfig;
import com.pd.finance.exceptions.PersistenceException;
import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityIdentifier;
import com.pd.finance.service.equityattribute.EquityHistoricalStockPriceAttributeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HistoricalStockPriceService extends AbstractHttpService implements IHistoricalStockPriceService {
    private static final Logger logger = LoggerFactory.getLogger(EquityHistoricalStockPriceAttributeService.class);


    @Autowired
    private ApplicationConfig config;
    @Autowired
    private IEquityService equityService;

    @Autowired
    private EquityHistoricalStockPriceAttributeService historicalStockPriceAttributeService;

    @Override
    public Equity updateEquityHistoricalStockPrice(Equity equity) throws ServiceException{
        try {

            EquityIdentifier identifier = equity.getDefaultEquityIdentifier();
            historicalStockPriceAttributeService.enrichEquity(identifier,equity);
            equityService.upsertEquity(equity);
            return equity;

        } catch (ServiceException | PersistenceException e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(e.getMessage(),e);
        }
    }

    @Override
    public void updateAllEquityHistoricalStockPrice(String exchange) throws ServiceException{
        try {

            List<Equity> equities = equityService.getEquities(exchange);
            for(Equity anEquity:equities){

                try {
                    updateEquityHistoricalStockPrice(anEquity);
                    logger.info("successfully updated historical stock price of equity {}",anEquity.getDefaultEquityIdentifier());
                } catch (ServiceException e) {
                    logger.info("failed to update historical stock price of equity {}",anEquity.getDefaultEquityIdentifier());
                }
            }


        } catch (PersistenceException e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(e.getMessage(),e);
        }
    }

}
