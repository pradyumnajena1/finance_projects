package com.pd.finance.service;

import com.pd.finance.exceptions.EquityNotFoundException;
import com.pd.finance.exceptions.PersistenceException;
import com.pd.finance.model.Equity;

import java.util.List;

public interface IEquityService {
    Equity getEquity(String id) throws EquityNotFoundException;

    Equity getEquityByNseId(String nseId)throws EquityNotFoundException, PersistenceException;

    Equity getEquityByBseId(String nseId)throws EquityNotFoundException,PersistenceException;

    Equity getEquityByName(String name)throws EquityNotFoundException,PersistenceException;

    List<Equity> getEquitiesByNames(List<String> names)throws EquityNotFoundException,PersistenceException;

    Equity upsertEquity(Equity equity)throws PersistenceException;

    Equity createEquity(Equity equity) throws PersistenceException;

    Equity updateEquity( Equity equity) throws PersistenceException;

    Equity deleteEquity(String id) throws PersistenceException;

    Equity deleteEquityByBseId(String bseId) throws PersistenceException;

    Equity deleteEquityByNseId(String nseId) throws PersistenceException;
}
