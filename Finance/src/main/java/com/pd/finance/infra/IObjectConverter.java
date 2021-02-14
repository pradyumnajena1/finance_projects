package com.pd.finance.infra;

import com.pd.finance.model.EquityStockExchangeDetails;
import com.pd.finance.request.CreateStockExchangeDetailsRequest;

public interface IObjectConverter {
    EquityStockExchangeDetails convert(CreateStockExchangeDetailsRequest exchangeDetailsRequest);
}
