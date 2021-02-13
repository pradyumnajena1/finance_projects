package com.pd.finance.service;

import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.model.EquityIdentifier;
import com.pd.finance.model.EquityStockExchangeDetails;

import java.util.List;

public interface IStockExchangeService {
    List<EquityStockExchangeDetails> getStockExchangeDetails(EquityIdentifier equityIdentifier) throws ServiceException;
}
