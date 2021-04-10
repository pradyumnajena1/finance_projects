package com.pd.finance.service;

import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.model.Equity;

public interface IEquitySummaryServiceService {
    void updateAllEquitySummary(String exchange) throws ServiceException;

    Equity updateEquitySummary(Equity equity)throws ServiceException;
}
