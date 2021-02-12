package com.pd.finance.service;

import com.pd.finance.config.ApplicationConfig;
import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.model.EquitySearchResponse;
import com.pd.finance.model.EquityStockExchangeDetails;
import com.pd.finance.model.EquityIdentifier;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StockExchangeService extends AbstractHttpService{
    private static final Logger logger = LoggerFactory.getLogger(StockExchangeService.class);

    @Autowired
    private ApplicationConfig config;

    public StockExchangeService() {

    }

    public List<EquityStockExchangeDetails> getStockExchangeDetails(EquityIdentifier equityIdentifier) throws ServiceException {

            String searchString  = equityIdentifier.getSearchString();
            String url =  String.format(config.getEnvProperty("StockExchangeDetailsUrl"),searchString);
            EquitySearchResponse searchResponse=  get(url, EquitySearchResponse.class);
            return  searchResponse.getStockExchangeDetails();
    }
}
