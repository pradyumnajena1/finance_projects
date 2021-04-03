package com.pd.finance.service.equityenricher;

import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityIdentifier;

public interface IEquityEnricherService {
    void enrichEquity(EquityIdentifier identifier, Equity equity) throws ServiceException;
}
