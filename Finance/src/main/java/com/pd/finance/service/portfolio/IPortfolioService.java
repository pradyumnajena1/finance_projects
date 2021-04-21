package com.pd.finance.service.portfolio;

import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.model.Equity;
import com.pd.finance.model.portfolio.Portfolio;
import com.pd.finance.request.CreatePortfolioRequest;
import com.pd.finance.request.UpdatePortfolioRequest;

public interface IPortfolioService {
    Portfolio createPortfolio(Long userId,CreatePortfolioRequest request) throws ServiceException;

    Portfolio getPortfolio(Long userId,Long id)throws ServiceException;

    Portfolio updatePortfolio(Long userId,Long id,UpdatePortfolioRequest request) throws ServiceException;

    Portfolio deletePortfolio(Long userId,Long id) throws ServiceException;
}
