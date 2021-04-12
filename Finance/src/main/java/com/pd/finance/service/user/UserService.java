package com.pd.finance.service.user;

import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.exceptions.UserNotFoundException;
import com.pd.finance.model.EncryptedPassword;
import com.pd.finance.model.user.User;
import com.pd.finance.persistence.UserRepository;
import com.pd.finance.request.UserLoginRequest;
import com.pd.finance.response.UserLoginResponse;
import com.pd.finance.service.SequenceGeneratorService;
import com.pd.finance.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService implements IUserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;


    @Override
    public User createUser(User user) throws ServiceException {
        user.setId(sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME));
        EncryptedPassword encryptedPassword = new EncryptedPassword(user.getPassword());

        user.setPassword(encryptedPassword.getEncryptedPassword());
        user.setSalt(encryptedPassword.getSalt());

        return userRepository.insert(user);
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
