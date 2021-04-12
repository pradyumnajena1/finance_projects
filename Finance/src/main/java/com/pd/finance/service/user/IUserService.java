package com.pd.finance.service.user;

import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.model.user.User;

import java.util.List;


public interface IUserService {
    User createUser(User user) throws ServiceException;

    User getUser(Long id) throws ServiceException;

    User updateUser(Long id, User userDetails) throws ServiceException;

    User deleteUser(Long id) throws ServiceException;
    List<User> getAllUsers()throws ServiceException;
}
