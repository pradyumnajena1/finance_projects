package com.pd.finance.service;

import com.pd.finance.exceptions.QueryNotFoundException;
import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.exceptions.UserNotFoundException;
import com.pd.finance.model.Equity;
import com.pd.finance.model.UserEquityQuery;
import com.pd.finance.persistence.UserQueryRepository;
import com.pd.finance.persistence.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserQueryService implements IUserQueryService{

    private static final Logger logger = LoggerFactory.getLogger(UserQueryService.class);

    @Autowired
    private UserQueryRepository userQueryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;
    @Autowired
    private IEquitySearchService equitySearchService;

    @Override
    public UserEquityQuery createUserEquityQuery(UserEquityQuery userEquityQuery) throws ServiceException {
        try {
            logger.info("createUserEquityQuery exec started for {}",userEquityQuery.getQueryName());
            Long userId = userEquityQuery.getUserId();
            if(userRepository.existsById(userId)){
                long id = sequenceGeneratorService.generateSequence(UserEquityQuery.SEQUENCE_NAME);
                userEquityQuery.setId(id);
                UserEquityQuery createdQuery = userQueryRepository.insert(userEquityQuery);
                logger.info("createUserEquityQuery exec completed successfully for {}",userEquityQuery.getQueryName());
                return createdQuery;
            }else {
                throw new UserNotFoundException("User with id:"+userId+" doesn't exist");
            }

        } catch (Exception exception) {
            logger.error(exception.getMessage(),exception);
            throw new ServiceException(exception);
        }
    }

    @Override
    public UserEquityQuery getUserEquityQuery(Long userId,Long id) throws ServiceException {
        try {
            logger.info("getUserEquityQuery exec started for userId:{} id:{}",userId,id );

                UserEquityQuery query = userQueryRepository.findByUserIdAndId(userId,id);
                logger.info("getUserEquityQuery exec completed for userId:{} id:{}",userId,id );
                return query;


        } catch (Exception exception) {
            logger.error(exception.getMessage(),exception);
            throw new ServiceException(exception);
        }
    }

    @Override
    public UserEquityQuery updateUserEquityQuery(Long userId,Long id, UserEquityQuery userQueryDetails) throws ServiceException {
        try {
            if(userRepository.existsById(userId)){
                if(userQueryRepository.existsById(id)){
                    UserEquityQuery byUserIdAndId = userQueryRepository.findByUserIdAndId(userId, id);
                    if(byUserIdAndId!=null){

                        byUserIdAndId.setQueryDescription(userQueryDetails.getQueryDescription());
                        byUserIdAndId.setSearchRequest(userQueryDetails.getSearchRequest());
                        byUserIdAndId.setQueryName(userQueryDetails.getQueryName());
                        userQueryRepository.save(byUserIdAndId);
                        return byUserIdAndId;


                    }else {
                        throw new QueryNotFoundException(" Query with id: "+id+"  doesn't belong to user with id: "+userId);
                    }

                }else {
                    throw new QueryNotFoundException(" Query with id: "+id+"  doesn't exist");
                }
            }else {
                throw new UserNotFoundException("User with id: "+userId+" doesn't exist");
            }
        } catch (UserNotFoundException | QueryNotFoundException e) {
           logger.error(e.getMessage(),e);
           throw new ServiceException(e);
        }


    }

    @Override
    public UserEquityQuery deleteUserEquityQuery(Long userId,Long id) throws ServiceException {
        try {
            logger.info("deleteUserEquityQuery exec started for userId:{} id:{}",userId,id );

            UserEquityQuery query = userQueryRepository.findByUserIdAndId(userId,id);
            if(query!=null){
                userQueryRepository.delete(query);
            }else {
                throw new QueryNotFoundException("User Query with userId: "+userId+" Id:"+id+" not found");
            }
            logger.info("deleteUserEquityQuery exec completed for userId:{} id:{}",userId,id );
            return query;


        } catch (Exception exception) {
            logger.error(exception.getMessage(),exception);
            throw new ServiceException(exception);
        }
    }

    @Override
    public List<Equity> executeUserEquityQuery(Long userId, Long userQueryId) throws ServiceException {
        List<Equity> equities = new ArrayList<>();
        try {
            logger.info("getUserEquityQuery exec started for userId:{} id:{}",userId,userQueryId );

            UserEquityQuery query = userQueryRepository.findByUserIdAndId(userId,userQueryId);
            if(query!=null){
                 equities = equitySearchService.search(query.getSearchRequest());
            }else {
                throw new QueryNotFoundException("Query with userId: "+userId+" queryId: "+userQueryId+" not found");
            }
            logger.info("getUserEquityQuery exec completed for userId:{} id:{}",userId,userQueryId );



        } catch (Exception exception) {
            logger.error(exception.getMessage(),exception);
            throw new ServiceException(exception);
        }
        return equities;
    }
}
