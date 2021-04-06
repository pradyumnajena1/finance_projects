package com.pd.finance.service.equityattribute;

import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityIdentifier;
import com.pd.finance.response.EquityStockExchangeDetailsResponse;
import com.pd.finance.service.IYahooService;
import com.pd.finance.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EquityStockExchangeDetailsAttributeService extends HttpGatewayEquityAttributeService {
    private static final Logger logger = LoggerFactory.getLogger(EquityStockExchangeDetailsAttributeService.class);

    @Autowired
    private IYahooService stockExchangeService;

    @Override
    public void enrichEquity(EquityIdentifier identifier, Equity equity) throws ServiceException {
        try {
            logger.info( "enrichEquity exec started for equity:{}",equity.getDefaultEquityIdentifier());

            EquityStockExchangeDetailsResponse stockExchangeDetails = stockExchangeService.getStockExchangeDetails(identifier);
            stockExchangeDetails.setSource(Constants.SOURCE_YAHOO_FINANCE);
            stockExchangeDetails.setUpdatedDate(new Date());
            equity.setStockExchangeDetails(stockExchangeDetails);
            logger.info("enrichEquity exec completed for equity:{}",equity.getDefaultEquityIdentifier());
        } catch (ServiceException e) {
           logger.error( "enrichEquity exec failed for equity:{}",e.getMessage(),e);
           throw new ServiceException(e);
        }
    }
}
