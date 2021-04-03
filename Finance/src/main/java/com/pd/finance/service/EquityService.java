package com.pd.finance.service;


import com.pd.finance.exceptions.EquityNotFoundException;
import com.pd.finance.exceptions.PersistenceException;
import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityIdentifier;
import com.pd.finance.model.SourceDetails;
import com.pd.finance.persistence.EquityRepository;
import com.pd.finance.request.EquityBulkUpdateRequest;
import com.pd.finance.response.chart.EquityBulkUpdateResponse;
import com.pd.finance.service.equityenricher.IEquityEnricherService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquityService implements IEquityService {
    private static final Logger logger = LoggerFactory.getLogger(EquityService.class);
    @Autowired
    private EquityRepository equityRepository;
    @Autowired
    private IEquityEnricherService equityEnricherService;

    @Autowired
    private ICacheService cacheService;

    @Override
    public Equity getEquity(final String id) throws EquityNotFoundException {

        Equity equity = cacheService.getEquity(id, equityId -> equityRepository.findById(equityId).orElse(null));

        return equity;
    }

    @Override
    public List<Equity> getEquities(final String exchange) throws PersistenceException {

        List<Equity> equities = equityRepository.findByExchange( exchange);
        return equities;
    }

    @Override
    public Equity getEquity(final EquityIdentifier equityIdentifier) throws EquityNotFoundException {

        Equity equity = equityRepository.findByExchangeAndSymbol(equityIdentifier.getExchange(), equityIdentifier.getSymbol());

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


            EquityIdentifier equityIdentifier = equity.getDefaultEquityIdentifier();

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

    @Override
    public EquityBulkUpdateResponse updateEquities(EquityBulkUpdateRequest request) {
        EquityBulkUpdateResponse result = new EquityBulkUpdateResponse();
        result.setCompleted(false);
        int successfulUpdates = 0;
        int numEquitiesToUpdate = 0;

        try {

            Pageable pageRequest = PageRequest.of(0, 100);
            Page<Equity> equitiesPage = equityRepository.findAll(pageRequest);
            while (!equitiesPage.isEmpty())
            {
                List<Equity> equities = equitiesPage.getContent();
                numEquitiesToUpdate+=equities.size();
                for(Equity anEquity:equities){

                    boolean success = fetchAndPersistEquity(anEquity);
                    if(success) successfulUpdates++;


                }
                pageRequest = pageRequest.next();
                equitiesPage = equityRepository.findAll(pageRequest);
            }
            result = getEquityBulkUpdateResponse(numEquitiesToUpdate, successfulUpdates);
        } catch (Exception exception) {
            logger.error(exception.getMessage(),exception);
            result.setCompleted(false);
        }

        return result;
    }
    @NotNull
    protected EquityBulkUpdateResponse getEquityBulkUpdateResponse(int numObjectsCreated, int successfulPersists) {
        EquityBulkUpdateResponse bulkUpdateResponse = new EquityBulkUpdateResponse();
        bulkUpdateResponse.setNumObjectsToUpdate(numObjectsCreated);
        bulkUpdateResponse.setNumObjectsUpdated(successfulPersists);
        bulkUpdateResponse.setCompleted(true);
        return bulkUpdateResponse;
    }
    private boolean fetchAndPersistEquity(Equity equity) {
        boolean success = false;
        try {
            logger.info("fetchAndPersistEquity exec started for equity {}",equity.getDefaultEquityIdentifier());


            EquityIdentifier identifier = equity.getDefaultEquityIdentifier();

            SourceDetails sourceDetails = equity.getDefaultSourceDetails();

            if(sourceDetails!=null){

                identifier.putAdditionalAttribute("url",sourceDetails.getSourceUrl());
            };

            equityEnricherService.enrichEquity(identifier,equity);
            upsertEquity(equity);
            Thread.sleep(2*1000);
            success = true;
            logger.info("fetchAndPersistEquity exec completed for equity {}",equity.getDefaultEquityIdentifier());

        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return success;
    }


}
