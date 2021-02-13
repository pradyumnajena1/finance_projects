package com.pd.finance.service;

import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityIdentifier;
import com.pd.finance.model.EquityStockExchangeDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
@Service
public class EquityStockExchangeDetailsAttributeService extends HttpGatewayEquityAttributeService {
    private static final Logger logger = LoggerFactory.getLogger(EquityStockExchangeDetailsAttributeService.class);

    @Autowired
    private IStockExchangeService stockExchangeService;

    @Override
    public void enrichEquity(EquityIdentifier identifier, Equity equity) throws ServiceException {
        try {
            logger.info( "enrichEquity exec started for equity:{}",equity.getName());
            List<EquityStockExchangeDetails> stockExchangeDetails = stockExchangeService.getStockExchangeDetails(identifier);
            equity.setStockExchangeDetails(stockExchangeDetails);
            logger.info("enrichEquity exec completed for equity:{}",equity.getName());
        } catch (ServiceException e) {
           logger.error( "enrichEquity exec failed for equity:{}",e.getMessage(),e);
           throw new ServiceException(e);
        }
    }
}
