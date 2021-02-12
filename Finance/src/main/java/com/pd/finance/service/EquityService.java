package com.pd.finance.service;


import com.pd.finance.exceptions.EquityNotFoundException;
import com.pd.finance.exceptions.PersistenceException;
import com.pd.finance.htmlscrapper.equity.EquitySwotFactory;
import com.pd.finance.model.Equity;
import com.pd.finance.persistence.EquityRepository;
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
    public Equity getEquityByNseId(final String nseId)throws EquityNotFoundException,PersistenceException{
        return getEquityByAlternateId(nseId , alternateId->equityRepository.findByNseId(alternateId));
    }
    @Override
    public Equity getEquityByBseId(final String bseId)throws EquityNotFoundException,PersistenceException{
        return getEquityByAlternateId(bseId , alternateId->equityRepository.findByBseId(alternateId));
    }

    private Equity getEquityByAlternateId(String alternateId, Function<String,Equity> providerByAlternateId) throws EquityNotFoundException {
        try {
            AtomicReference<Equity> equityAtomicReference = new AtomicReference<>();
            Optional<String> optional = cacheService.getEquityIdByBseId(alternateId);

            optional.ifPresent(equityId->equityAtomicReference.set(cacheService.getEquity(equityId, id -> equityRepository.findById(id).get())));
            optional.orElseGet(()->{
                Equity equity = providerByAlternateId.apply(alternateId);
                if(equity!=null){
                    equityAtomicReference.set(cacheService.getEquity(equity.getId(), id->equity));
                }
               return null;

            });

            return equityAtomicReference.get();
        } catch (NoSuchElementException e) {
            throw new EquityNotFoundException(e);
        }
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
            Equity equityFromDb = equityRepository.findByBseIdAndNseId(equity.getBseId(), equity.getNseId());
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
    @Override
    public Equity deleteEquityByBseId(String bseId) throws PersistenceException{

            AtomicReference<Equity> equity = new AtomicReference<>();
            Optional<String> equityIdByBseId = cacheService.getEquityIdByBseId(bseId);

                equityIdByBseId.ifPresent(equityId-> {
                    try {
                        equity.set(deleteEquity(equityId));
                    } catch (PersistenceException e) {
                       logger.error(e.getMessage(),e);
                    }
                });

            return equity.get();

    }
    @Override
    public Equity deleteEquityByNseId(String nseId) throws PersistenceException{
        AtomicReference<Equity> equity = new AtomicReference<>();
        Optional<String> equityIdByBseId = cacheService.getEquityIdByNseId(nseId);

        equityIdByBseId.ifPresent(equityId-> {
            try {
                equity.set(deleteEquity(equityId));
            } catch (PersistenceException e) {
                logger.error(e.getMessage(),e);
            }
        });

        return equity.get();
    }
}
