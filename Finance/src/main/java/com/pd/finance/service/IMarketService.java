package com.pd.finance.service;

import com.pd.finance.model.Equity;
import com.pd.finance.request.MarketGainersRequest;

import java.util.List;

public interface IMarketService {

    List<Equity> GetGainers(MarketGainersRequest request) throws Exception;
}
