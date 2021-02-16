package com.pd.finance.infra;

import com.pd.finance.model.EquityStockExchangeDetails;
import com.pd.finance.request.CreateStockExchangeDetailsRequest;
import com.pd.finance.response.EquityStockExchangeDetailsResponse;

public interface IObjectConverter {
    EquityStockExchangeDetails convert(CreateStockExchangeDetailsRequest exchangeDetailsRequest);
    EquityStockExchangeDetails convert(EquityStockExchangeDetailsResponse detailsResponse);
}
