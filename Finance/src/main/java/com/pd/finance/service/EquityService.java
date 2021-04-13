package com.pd.finance.service;


import com.pd.finance.exceptions.EquityNotFoundException;
import com.pd.finance.exceptions.PersistenceException;
import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.filter.code.EquityNamesFilter;
import com.pd.finance.filter.db.EquityExchangeFilter;
import com.pd.finance.filter.db.EquityUpdateDateFilter;
import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityIdentifier;
import com.pd.finance.model.EquityIdentifiers;
import com.pd.finance.model.SourceDetails;
import com.pd.finance.persistence.EquityRepository;
import com.pd.finance.request.BulkCreateEquityRequest;
import com.pd.finance.request.CreateEquityRequest;
import com.pd.finance.request.EquityBulkUpdateRequest;
import com.pd.finance.response.EquityStockExchangeDetailsResponse;
import com.pd.finance.response.chart.EquityBulkUpdateResponse;
import com.pd.finance.service.equityenricher.IEquityEnricherService;
import com.pd.finance.utils.Constants;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class EquityService implements IEquityService {
    private static final Logger logger = LoggerFactory.getLogger(EquityService.class);

    @Autowired
    private EquityRepository equityRepository;
    @Autowired
    private IEquityEnricherService equityEnricherService;

    @Autowired
    private ICacheService cacheService;

    @Autowired
    private IYahooService stockExchangeService;

    @Override
    public Equity getEquity(final String id) throws EquityNotFoundException {

        Equity equity = cacheService.getEquity(id, equityId -> equityRepository.findById(equityId).orElse(null));

        return equity;
    }

    @Override
    public Page<Equity> getEquities(final String exchange, Pageable pageRequest) throws PersistenceException {

        Page<Equity> equities = equityRepository.findByExchange(exchange, pageRequest);
        return equities;
    }

    @Override
    public Equity getEquity(final EquityIdentifier equityIdentifier) throws EquityNotFoundException {

        Equity equity = equityRepository.findByExchangeAndSymbol(equityIdentifier.getExchange(), equityIdentifier.getSymbol());

        return equity;
    }


    @Override
    public Equity getEquityByName(final String name) throws EquityNotFoundException, PersistenceException {

        return equityRepository.findByName(name);

    }

    @Override
    public List<Equity> getEquitiesByNames(final List<String> names) throws EquityNotFoundException, PersistenceException {

        return equityRepository.findByNameIn(names);

    }

    @Override
    public Equity upsertEquity(Equity equity) throws PersistenceException {
        try {


            EquityIdentifier equityIdentifier = equity.getDefaultEquityIdentifier();

            Equity equityFromDb = equityRepository.findByExchangeAndSymbol(equityIdentifier.getExchange(), equityIdentifier.getSymbol());

            if (equityFromDb != null) {
                equity.setId(equityFromDb.getId());
            }
            return equityRepository.save(equity);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new PersistenceException(e);
        }
    }

    @Override
    public Equity createEquity(Equity equity) throws PersistenceException {
        try {

            return equityRepository.save(equity);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<Equity> createEquity(BulkCreateEquityRequest bulkCreateEquityRequest) throws ServiceException {
        logger.info("createEquity exec started for bulkCreateEquityRequest");
        List<Equity> result = new ArrayList<>();
        List<CreateEquityRequest> createEquityRequests = bulkCreateEquityRequest.getEquities();
        createEquityRequests.parallelStream().forEach(createEquityRequest->{

            try {
                Equity equity = createEquity(createEquityRequest);
                result.add(equity);
            } catch (ServiceException e) {
                logger.info("createEquity exec failed for CreateEquityRequest {}",createEquityRequest);
            }
        });
        logger.info("createEquity exec completed for bulkCreateEquityRequest");
        return result;
    }

    @Override
    public Equity createEquity(CreateEquityRequest request) throws ServiceException {
        Equity equity = null;
        logger.info("createEquity exec started for CreateEquityRequest {}",request);
        try {
            equity = createEquityWithMandatoryAttributes(request.getName(), request.getSrcUrl(), request.getExchange(), Constants.SOURCE_MONEY_CONTROL);
            equity =  upsertEquity(equity);
            boolean success   =  fetchAndPersistEquityAttributes(equity);
            if(success){
                equity = getEquity(equity.getDefaultEquityIdentifier());
            }
            logger.info("createEquity exec completed for CreateEquityRequest {}",request);
        } catch ( Exception e) {
            logger.error(e.getMessage(), e);
            logger.info("createEquity exec failed for CreateEquityRequest {}",request);
            throw new ServiceException(e);
        }
        return equity;
    }

    @Override
    public Equity updateEquity(Equity equity) throws PersistenceException {

        try {
            return equityRepository.save(equity);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new PersistenceException(e);
        }
    }

    @Override
    public Equity deleteEquity(final String id) throws PersistenceException {
        Optional<Equity> byId = equityRepository.findById(id);
        byId.ifPresent(equity -> equityRepository.delete(equity));
        return byId.orElseThrow(() -> new PersistenceException(String.format("Equity with id {} not present", id)));
    }

    @Override
    public EquityBulkUpdateResponse updateEquities(EquityBulkUpdateRequest request) {
        EquityBulkUpdateResponse result = new EquityBulkUpdateResponse();
        result.setCompleted(false);
        AtomicInteger successfulUpdates = new AtomicInteger(0);
        AtomicInteger currentEquityNumber = new AtomicInteger(0);
        int maxEquitiesToUpdate  = -1;
        if(request.getDebugFilter()!=null){
            maxEquitiesToUpdate = request.getDebugFilter().getNumEquities();

        }


        try {
            Criteria criteria = getBulkUpdateFilterCriteria(request);
            Pageable pageRequest = PageRequest.of(0, 100);
            Page<Equity> equitiesPage = getPage(criteria, pageRequest);
            final int numEquitiesToUpdate = (int) equitiesPage.getTotalElements();
            final int totalPages = equitiesPage.getTotalPages();

            logger.info("updateEquities No of equities to update {} no of pages {}", numEquitiesToUpdate, totalPages);
            while (!equitiesPage.isEmpty()) {
                int currentPageNumber = equitiesPage.getNumber();
                logger.info("updateEquities process started pageNumber {} out of {} pages  ", currentPageNumber, totalPages);

                List<Equity> equities = equitiesPage.getContent();
                logger.info("updateEquities  pageNumber {} numEquities {} ", currentPageNumber, equities.size());
                equities.parallelStream().forEach(anEquity -> {

                    currentEquityNumber.incrementAndGet();
                    boolean success = fetchAndPersistEquityAttributes(anEquity);
                    if (success) {
                        successfulUpdates.incrementAndGet();

                        logger.info("successfully updated  attributes of  equity {}", anEquity.getDefaultEquityIdentifier());
                        logger.info("updateEquities numSuccessfulUpdates:{}", successfulUpdates);


                    }
                    logger.info("updateEquities currentEquityNumber:{} numEquitiesToUpdate:{} pageNumber:{} totalPages:{}", currentEquityNumber , numEquitiesToUpdate, currentPageNumber, totalPages);


                });
                if(maxEquitiesToUpdate!=-1&& successfulUpdates.get()>maxEquitiesToUpdate){
                    break;
                }


                logger.info("updateEquities process completed pageNumber {}", currentPageNumber);
                pageRequest = pageRequest.next();
                equitiesPage = getPage(criteria, pageRequest);
            }
            result = getEquityBulkUpdateResponse(numEquitiesToUpdate, successfulUpdates.get());
        } catch (Exception exception) {
            logger.error(exception.getMessage(), exception);
            result.setCompleted(false);
        }

        return result;
    }

    private Criteria getBulkUpdateFilterCriteria(EquityBulkUpdateRequest request) {
        List<Criteria> criteriaList = new ArrayList<>();
        EquityExchangeFilter exchangeFilter = request.getExchangeFilter();
        if (exchangeFilter != null) {

            criteriaList.add(exchangeFilter.getCriteria(""));
        }
        EquityNamesFilter namesFilter = request.getNamesFilter();
        if (namesFilter != null) {
            criteriaList.add(namesFilter.getCriteria(""));
        }

        EquityUpdateDateFilter updateDateFilter = request.getUpdateDateFilter();
        if (updateDateFilter != null) {
            criteriaList.add(updateDateFilter.getCriteria(""));
        }

        Criteria criteria = null;
        if (criteriaList.size() > 1) {
            criteria = new Criteria().andOperator(criteriaList.toArray(new Criteria[criteriaList.size()]));
        } else if (criteriaList.size() == 1) {
            criteria = criteriaList.get(0);
        }


        return criteria;
    }

    protected Page<Equity> getPage(Criteria dbFilterCriteria, Pageable pageRequest) {

        Sort sort = Sort.by(Sort.Direction.ASC, "updatedDate");

        return dbFilterCriteria != null ? equityRepository.searchEquity(dbFilterCriteria, pageRequest) : equityRepository.findAll(pageRequest);
    }

    @NotNull
    protected EquityBulkUpdateResponse getEquityBulkUpdateResponse(int numObjectsCreated, int successfulPersists) {
        EquityBulkUpdateResponse bulkUpdateResponse = new EquityBulkUpdateResponse();
        bulkUpdateResponse.setNumObjectsToUpdate(numObjectsCreated);
        bulkUpdateResponse.setNumObjectsUpdated(successfulPersists);
        bulkUpdateResponse.setCompleted(true);
        return bulkUpdateResponse;
    }

    @Override
    public boolean fetchAndPersistEquityAttributes(Equity equity) {
        boolean success = false;
        try {
            logger.info("fetchAndPersistEquity exec started for equity {}", equity.getDefaultEquityIdentifier());


            EquityIdentifier identifier = equity.getDefaultEquityIdentifier();

            SourceDetails sourceDetails = equity.getDefaultSourceDetails();

            if (sourceDetails != null) {

                identifier.putAdditionalAttribute("url", sourceDetails.getSourceUrl());
            }
            ;

            equityEnricherService.enrichEquity(identifier, equity);
            equity.setUpdatedDate(new Date());
            upsertEquity(equity);
            //Thread.sleep(500);
            success = true;
            logger.info("fetchAndPersistEquity exec completed for equity {}", equity.getDefaultEquityIdentifier());

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return success;
    }


    @Override
    public Equity createEquityWithMandatoryAttributes(String extractedName, String equitySourceUrl, String exchange, String source) {

        Equity result = null;

        try {

            logger.info("createEquityWithMandatoryAttributes exec started for equity extractedName {}", extractedName);

            EquityIdentifier equityIdentifier = new EquityIdentifier(extractedName, exchange, source);

            EquityStockExchangeDetailsResponse stockExchangeDetails = stockExchangeService.getStockExchangeDetails(equityIdentifier);

            if (stockExchangeDetails != null) {

                SourceDetails sourceDetails = getSourceDetails(extractedName, equitySourceUrl);
                EquityIdentifiers equityIdentifiers = getEquityIdentifiers(extractedName, stockExchangeDetails);

                Equity equity = new Equity();
                equity.getSourceDetails().addSourceDetails(sourceDetails);
                equity.setEquityIdentifiers(equityIdentifiers);
                equity.setStockExchangeDetails(stockExchangeDetails);

                logger.info("createEquity completed creating equity {}", equity.getEquityIdentifiers());
                result = equity;
            } else {

                logger.info("createEquityWithMandatoryAttributes could not find exchange details  for equity extractedName {}", extractedName);
            }
            logger.info("createEquityWithMandatoryAttributes exec completed for equity extractedName {}", extractedName);
        } catch (Exception ex) {
            logger.error("createEquityWithMandatoryAttributes exec failed for equity extractedName {}", extractedName, ex);
        }
        return result;
    }



    @NotNull
    private EquityIdentifiers getEquityIdentifiers(String equityName, EquityStockExchangeDetailsResponse details) {
        EquityIdentifiers equityIdentifiers = new EquityIdentifiers();

        if (StringUtils.isBlank(equityName)) {
            equityName = StringUtils.isNotBlank(details.getShortName()) ? details.getShortName() : details.getLongName();
        }


        EquityIdentifier equityIdentifier = new EquityIdentifier(equityName, details.getExchange(), Constants.SOURCE_MONEY_CONTROL);
        equityIdentifier.setSymbol(details.getSymbol());

        logger.info("createEquity started creating equity {}", equityName);


        equityIdentifiers.getIdentifiers().add(equityIdentifier);
        return equityIdentifiers;
    }

    @NotNull
    private SourceDetails getSourceDetails(String equityName, String sourceUrl) {
        SourceDetails sourceDetails = new SourceDetails();
        sourceDetails.setEquityName(equityName);
        sourceDetails.setSourceUrl(sourceUrl);
        sourceDetails.setSourceName(Constants.SOURCE_MONEY_CONTROL);
        return sourceDetails;
    }


}
