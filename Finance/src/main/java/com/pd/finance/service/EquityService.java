package com.pd.finance.service;


import com.pd.finance.exceptions.EquityNotFoundException;
import com.pd.finance.exceptions.PersistenceException;
import com.pd.finance.htmlscrapper.equity.EquitySwotFactory;
import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityIdentifier;
import com.pd.finance.model.SourceDetails;
import com.pd.finance.persistence.EquityRepository;
import com.pd.finance.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

@Service
public class EquityService implements IEquityService {
    private static final Logger logger = LoggerFactory.getLogger(EquityService.class);
    @Autowired
    private EquityRepository equityRepository;

    @Autowired
    private ICacheService cacheService;

    @Override
    public Equity getEquity(final String id) throws EquityNotFoundException {

        Equity equity = cacheService.getEquity(id, equityId -> equityRepository.findById(equityId).orElse(null));

        return equity;
    }


    @Override
    public Equity getEquityByName(final String name)throws EquityNotFoundException,PersistenceException{

        return  equityRepository.findByName(name);

    }

    @Override
    public List<Equity> getEquitiesByNames(final List<String> names)throws EquityNotFoundException,PersistenceException{

        return  equityRepository.findByNameIn(names);

    }
    @Override
    public Equity upsertEquity(Equity equity)throws PersistenceException{
        try {


            EquityIdentifier equityIdentifier = equity.getEquityIdentifiers().getEquityIdentifier(Constants.SOURCE_MONEY_CONTROL);

            Equity equityFromDb = equityRepository.findByExchangeAndSymbol(equityIdentifier.getExchange(), equityIdentifier.getSymbol());

            if(equityFromDb!=null){
                equity.setId(equityFromDb.getId());
            }
           return equityRepository.save(equity);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new PersistenceException(e);
        }
    }

    @Override
    public Equity createEquity(Equity equity) throws PersistenceException {
        try {

            return equityRepository.save(equity);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new PersistenceException(e);
        }
    }

    @Override
    public Equity updateEquity(Equity equity) throws PersistenceException{

        try {
            return equityRepository.save(equity);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new PersistenceException(e);
        }
    }
    @Override
    public Equity deleteEquity(final String id) throws PersistenceException{
        Optional<Equity> byId = equityRepository.findById(id);
        byId.ifPresent(equity -> equityRepository.delete(equity));
        return byId.orElseThrow(()->new PersistenceException(String.format("Equity with id {} not present",id)));
    }


}
