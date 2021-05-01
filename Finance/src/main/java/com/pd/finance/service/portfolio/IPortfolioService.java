package com.pd.finance.service.portfolio;

import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.model.Equity;
import com.pd.finance.model.portfolio.Portfolio;
import com.pd.finance.request.*;

public interface IPortfolioService {
    Portfolio createPortfolio(Long userId,CreatePortfolioRequest request) throws ServiceException;

    Portfolio getPortfolio(Long userId,Long id)throws ServiceException;

    Portfolio updatePortfolio(Long userId,Long id,UpdatePortfolioRequest request) throws ServiceException;

    Portfolio deletePortfolio(Long userId,Long id) throws ServiceException;

    Portfolio addPortfolioEquities(Long userId, Long portfolioId, AddPortfolioEquityRequest request)throws ServiceException;

    Portfolio deletePortfolioEquities(Long userId, Long portfolioId, DeletePortfolioEquityRequest request) throws ServiceException;

    Portfolio addPortfolioEquityLots(Long userId, Long portfolioId, String portfolioEquityId, AddPortfolioEquityLotsRequest request)throws ServiceException;

    Portfolio deletePortfolioEquityLots(Long userId, Long portfolioId, String portfolioEquityId, DeletePortfolioEquityLotsRequest request)throws ServiceException;
}
