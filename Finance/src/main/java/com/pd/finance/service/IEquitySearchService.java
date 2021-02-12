package com.pd.finance.service;

import com.pd.finance.model.Equity;
import com.pd.finance.request.EquitySearchRequest;

import java.util.List;

public interface IEquitySearchService {
    List<Equity> search(EquitySearchRequest searchRequest);
}
