package com.pd.finance.service;

import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.exceptions.UserNotFoundException;
import com.pd.finance.model.UserEquityQuery;
import com.pd.finance.model.user.User;
import com.pd.finance.persistence.UserQueryRepository;
import com.pd.finance.persistence.UserRepository;
import com.pd.finance.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserQueryService implements IUserQueryService{

    private static final Logger logger = LoggerFactory.getLogger(UserQueryService.class);

    @Autowired
    private UserQueryRepository userQueryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

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
        return null;
    }

    @Override
    public UserEquityQuery deleteUserEquityQuery(Long userId,Long id) throws ServiceException {
        return null;
    }
}
