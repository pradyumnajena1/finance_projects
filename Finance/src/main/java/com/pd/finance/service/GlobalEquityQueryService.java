package com.pd.finance.service;

import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.model.GlobalEquityQuery;
import com.pd.finance.persistence.GlobalEquityQueryRepository;
import com.pd.finance.request.EquitySearchRequest;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigInteger;
import java.util.Date;
import java.util.Optional;
@Service
public class GlobalEquityQueryService implements IGlobalEquityQueryService{
    private static final Logger logger = LoggerFactory.getLogger(GlobalEquityQueryService.class);

    @Autowired
    private GlobalEquityQueryRepository globalEquityQueryRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;
    private ExampleMatcher matcher = getExampleMatcher();

    @Override
    public GlobalEquityQuery createGlobalEquityQuery(EquitySearchRequest searchRequest) throws ServiceException {
        try {
            logger.info("createGlobalEquityQuery exec started for {}",searchRequest);
                GlobalEquityQuery globalEquityQuery = new GlobalEquityQuery();
                globalEquityQuery.setSearchRequest(searchRequest);
                long id = sequenceGeneratorService.generateSequence(GlobalEquityQuery.SEQUENCE_NAME);
                globalEquityQuery.setId(id);
                globalEquityQuery.setCreatedDate(new Date());

                GlobalEquityQuery createdQuery = globalEquityQueryRepository.insert(globalEquityQuery);
                logger.info("createGlobalEquityQuery exec completed successfully for {}",searchRequest );
                return createdQuery;


        } catch (Exception exception) {
            logger.error(exception.getMessage(),exception);
            throw new ServiceException(exception);
        }


    }
    @Override
    public GlobalEquityQuery findBySearchRequest(EquitySearchRequest searchRequest) throws ServiceException {
        GlobalEquityQuery result = null;
        try {
            GlobalEquityQuery globalEquityQuery = new GlobalEquityQuery();
            globalEquityQuery.setSearchRequest(searchRequest);
            Example<GlobalEquityQuery> example = Example.of(globalEquityQuery, matcher);
            Optional<GlobalEquityQuery> findOne = globalEquityQueryRepository.findOne(example);
            if(findOne.isPresent()){
                result = findOne.get();
            }
        } catch (Exception exception) {
            logger.error(exception.getMessage(),exception);
            throw new ServiceException(exception);
        }
        return result;
    }

    @Override
    public GlobalEquityQuery getGlobalEquityQueryById(Long queryId) throws ServiceException {
        GlobalEquityQuery result = null;
        try {

            Optional<GlobalEquityQuery> findOne = globalEquityQueryRepository.findById(queryId)  ;
            if(findOne.isPresent()){
                result = findOne.get();
            }
        } catch (Exception exception) {
            logger.error(exception.getMessage(),exception);
            throw new ServiceException(exception);
        }
        return result;
    }
    @Override
    public GlobalEquityQuery updateGlobalEquityQueryRatings(Long queryId, int numStars) throws ServiceException {
        GlobalEquityQuery result = null;
        try {

            Optional<GlobalEquityQuery> findOne = globalEquityQueryRepository.findById(queryId)  ;
            if(findOne.isPresent()){
                result = findOne.get();
                result.updateStarRating(numStars);
                globalEquityQueryRepository.save(result);
            }else{
                throw new EntityNotFoundException("GlobalEquityQuery entity not found with ID:"+queryId);
            }
        } catch (Exception exception) {
            logger.error(exception.getMessage(),exception);
            throw new ServiceException(exception);
        }
        return result;
    }

    @Override
    public GlobalEquityQuery updateGlobalEquityQueryLastExecTime(Long queryId) throws ServiceException {
        GlobalEquityQuery result = null;
        try {

            Optional<GlobalEquityQuery> findOne = globalEquityQueryRepository.findById(queryId)  ;
            if(findOne.isPresent()){
                result = findOne.get();
                result.recordLatestExecutionTime();
                globalEquityQueryRepository.save(result);
            }else{
                throw new EntityNotFoundException("GlobalEquityQuery entity not found with ID:"+queryId);
            }
        } catch (Exception exception) {
            logger.error(exception.getMessage(),exception);
            throw new ServiceException(exception);
        }
        return result;
    }

    @Override
    public void recordGlobalEquityQueryExecution(EquitySearchRequest searchRequest) {
        try {
            GlobalEquityQuery globalEquityQuery = findBySearchRequest(searchRequest);
            if(globalEquityQuery == null){
                globalEquityQuery=  createGlobalEquityQuery(searchRequest);
            }
            updateGlobalEquityQueryLastExecTime(globalEquityQuery.getId());
        } catch (ServiceException e) {
           logger.error(e.getMessage(),e);
        }
    }


    @NotNull
    private ExampleMatcher getExampleMatcher() {

        return ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnorePaths("numExecutions","numOneStarRatings","numTwoStarRatings","numThreeStarRatings","numFourStarRatings","numFiveStarRatings","totalNumRatings","avgRatings","avgNumExecutionsPerDay","lastExecutionDate","updatedDate","createdDate");
    }
}
