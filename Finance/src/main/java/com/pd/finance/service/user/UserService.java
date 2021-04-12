package com.pd.finance.service.user;

import com.mongodb.MongoWriteException;
import com.pd.finance.exceptions.DuplicateEntityException;
import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.exceptions.UserNotFoundException;
import com.pd.finance.model.EncryptedPassword;
import com.pd.finance.model.user.User;
import com.pd.finance.persistence.UserRepository;
import com.pd.finance.request.UserLoginRequest;
import com.pd.finance.response.UserLoginResponse;
import com.pd.finance.service.SequenceGeneratorService;
import com.pd.finance.service.portfolio.PortfolioService;
import com.pd.finance.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService implements IUserService{
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;


    @Override
    public User createUser(User user) throws ServiceException {
        try {
            user.setId(sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME));
            EncryptedPassword encryptedPassword = new EncryptedPassword(user.getPassword());

            user.setPassword(encryptedPassword.getEncryptedPassword());
            user.setSalt(encryptedPassword.getSalt());

            User createdUser = userRepository.insert(user);
            return createdUser;
        } catch (Exception exception) {
            logger.error(exception.getMessage(),exception);

            if(exception instanceof DuplicateKeyException){

                throw new ServiceException(new  DuplicateEntityException("User with email:"+user.getEmail()+" exists" ));
            }
            throw new ServiceException(exception.getMessage(),exception);
        }
    }

    @Override
    public User getUser(Long userId) throws ServiceException {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found for this id :: " + userId));
            return user;
        } catch (UserNotFoundException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User updateUser(Long userId, User userDetails) throws ServiceException {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found for this id :: " + userId));

            user.setUserName(userDetails.getUserName());
            user.setEmail(userDetails.getEmail());
            user.setMobile(userDetails.getMobile());
            final User updatedUser = userRepository.save(user);
            return updatedUser;
        } catch (UserNotFoundException e) {
          throw new ServiceException(e);
        }
    }

    @Override
    public User deleteUser(Long userId) throws ServiceException {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found for this id :: " + userId));

            userRepository.delete(user);
            return user;
        } catch (UserNotFoundException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> getAllUsers() throws ServiceException {
        return userRepository.findAll();
    }

    @Override
    public UserLoginResponse loginUser(UserLoginRequest loginRequest) throws ServiceException {

        try {
            String email = loginRequest.getEmail();
            User user = userRepository.findByEmail(email);
            if(user==null){
                return new UserLoginResponse(false,"user with email:"+email+" doesn't exist!");

            }

            EncryptedPassword encryptedPassword = new EncryptedPassword(user.getPassword(),user.getSalt());
            boolean isValidPassword = encryptedPassword.validatePassword(loginRequest.getPassword());
            if(!isValidPassword){
                return new UserLoginResponse(false,"Invalid Password!");

            }

            return new UserLoginResponse(true,"");


        } catch (Exception exception) {
            return new UserLoginResponse(false,exception.getMessage());
        }


    }
}
