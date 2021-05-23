package com.pd.finance.service;

import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.model.GlobalEquityQuery;
import com.pd.finance.request.EquitySearchRequest;

public interface IGlobalEquityQueryService {
    GlobalEquityQuery createGlobalEquityQuery(EquitySearchRequest searchRequest) throws ServiceException;

    GlobalEquityQuery findBySearchRequest(EquitySearchRequest searchRequest) throws ServiceException;

    GlobalEquityQuery getGlobalEquityQueryById(Long queryId)throws ServiceException;

    GlobalEquityQuery updateGlobalEquityQueryRatings(Long queryId, int numStars) throws ServiceException;

    GlobalEquityQuery updateGlobalEquityQueryLastExecTime(Long queryId) throws ServiceException;

    void recordGlobalEquityQueryExecution(EquitySearchRequest searchRequest);
}
