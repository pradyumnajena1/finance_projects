package com.pd.finance.service.portfolio;

import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.model.Equity;
import com.pd.finance.model.portfolio.Portfolio;
import com.pd.finance.request.CreatePortfolioRequest;
import com.pd.finance.request.UpdatePortfolioRequest;

public interface IPortfolioService {
    Portfolio createPortfolio(CreatePortfolioRequest request) throws ServiceException;

    Portfolio getPortfolio(String id)throws ServiceException;

    Portfolio updatePortfolio(String id,UpdatePortfolioRequest request) throws ServiceException;

    Portfolio deletePortfolio(String id) throws ServiceException;
}
