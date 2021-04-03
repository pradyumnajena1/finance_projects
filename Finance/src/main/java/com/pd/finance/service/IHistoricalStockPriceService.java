package com.pd.finance.service;

import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.model.Equity;

public interface IHistoricalStockPriceService {
    Equity updateEquityHistoricalStockPrice(Equity equity) throws ServiceException;

    void updateAllEquityHistoricalStockPrice(String exchange) throws ServiceException;


}
