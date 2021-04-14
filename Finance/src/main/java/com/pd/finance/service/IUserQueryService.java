package com.pd.finance.service;

import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.model.UserEquityQuery;
import com.pd.finance.model.user.User;

public interface IUserQueryService {

    UserEquityQuery createUserEquityQuery(UserEquityQuery user) throws ServiceException;

    UserEquityQuery getUserEquityQuery(Long userId, Long id) throws ServiceException;

    UserEquityQuery updateUserEquityQuery(Long userId,Long id, UserEquityQuery userQueryDetails) throws ServiceException;

    UserEquityQuery deleteUserEquityQuery(Long userId,Long id) throws ServiceException;
}
