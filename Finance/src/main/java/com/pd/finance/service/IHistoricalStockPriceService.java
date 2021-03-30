package com.pd.finance.service;

import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityHistoricalData;
import com.pd.finance.model.EquityIdentifier;

import java.time.temporal.TemporalAmount;

public interface IHistoricalStockPriceService {
    Equity updateEquityHistoricalStockPrice(Equity equity) throws ServiceException;

    void updateAllEquityHistoricalStockPrice() throws ServiceException;

    public EquityHistoricalData getHistoricalStockPrice(EquityIdentifier identifier, TemporalAmount amountToSubtract) throws ServiceException;
}
