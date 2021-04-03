package com.pd.finance.service;

import com.pd.finance.exceptions.EquityNotFoundException;
import com.pd.finance.exceptions.PersistenceException;
import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityIdentifier;
import com.pd.finance.request.EquityBulkUpdateRequest;
import com.pd.finance.response.chart.EquityBulkUpdateResponse;

import java.util.List;

public interface IEquityService {


    Equity getEquity(String id) throws EquityNotFoundException;
    List<Equity> getEquities(String exchange) throws PersistenceException;

    Equity getEquity(EquityIdentifier equityIdentifier) throws EquityNotFoundException;

    Equity getEquityByName(String name)throws EquityNotFoundException,PersistenceException;

    List<Equity> getEquitiesByNames(List<String> names)throws EquityNotFoundException,PersistenceException;

    Equity upsertEquity(Equity equity)throws PersistenceException;

    Equity createEquity(Equity equity) throws PersistenceException;

    Equity updateEquity( Equity equity) throws PersistenceException;

    Equity deleteEquity(String id) throws PersistenceException;


    EquityBulkUpdateResponse updateEquities(EquityBulkUpdateRequest request) throws ServiceException;
}
