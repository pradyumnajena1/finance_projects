package com.pd.finance.service;

import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.model.Equity;
import com.pd.finance.model.UserEquityQuery;

import java.util.List;

public interface IUserQueryService {

    UserEquityQuery createUserEquityQuery(UserEquityQuery user) throws ServiceException;

    UserEquityQuery getUserEquityQuery(Long userId, Long id) throws ServiceException;

    UserEquityQuery updateUserEquityQuery(Long userId,Long id, UserEquityQuery userQueryDetails) throws ServiceException;

    UserEquityQuery deleteUserEquityQuery(Long userId,Long id) throws ServiceException;

    List<Equity> executeUserEquityQuery(Long userId, Long userQueryId)throws ServiceException;

    List<UserEquityQuery> getAllUserEquityQuery(Long userId)throws ServiceException;
}
