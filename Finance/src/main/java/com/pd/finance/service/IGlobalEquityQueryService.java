package com.pd.finance.service;

import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.model.GlobalEquityQuery;
import com.pd.finance.request.EquitySearchRequest;
import com.pd.finance.response.PaginatedResponse;

public interface IGlobalEquityQueryService {
    GlobalEquityQuery createGlobalEquityQuery(EquitySearchRequest searchRequest) throws ServiceException;

    GlobalEquityQuery findBySearchRequest(EquitySearchRequest searchRequest) throws ServiceException;

    GlobalEquityQuery getGlobalEquityQueryById(Long queryId)throws ServiceException;

    PaginatedResponse<GlobalEquityQuery> getAllGlobalEquityQueries(int pageNum, int pageSize) throws ServiceException;

    GlobalEquityQuery updateGlobalEquityQueryRatings(Long queryId, int numStars) throws ServiceException;

    GlobalEquityQuery updateGlobalEquityQueryLastExecTime(Long queryId) throws ServiceException;

    void recordGlobalEquityQueryExecution(EquitySearchRequest searchRequest);
}
