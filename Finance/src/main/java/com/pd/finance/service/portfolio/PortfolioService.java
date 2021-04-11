package com.pd.finance.service.portfolio;

import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.model.portfolio.Portfolio;
import com.pd.finance.persistence.PortfolioRepository;
import com.pd.finance.request.CreatePortfolioRequest;
import com.pd.finance.request.UpdatePortfolioRequest;
import com.pd.finance.service.EquityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PortfolioService implements IPortfolioService{
    private static final Logger logger = LoggerFactory.getLogger(PortfolioService.class);


    @Autowired
    private PortfolioRepository portfolioRepository;

    @Override
    public Portfolio createPortfolio(CreatePortfolioRequest request) throws ServiceException {
        try {
            Portfolio portfolio = new Portfolio();
            portfolio.setName(request.getPortfolioName());
            portfolio.setUserId(request.getUserId());
            portfolio.setCreatedDate(new Date());
            portfolio.setUpdatedDate(new Date());
            Portfolio createdPortfolio = portfolioRepository.save(portfolio);
            return createdPortfolio;
        } catch (Exception exception) {

            logger.error(exception.getMessage(),exception);
            throw new ServiceException(exception.getMessage(),exception);
        }
    }

    @Override
    public Portfolio getPortfolio(String id) throws ServiceException {
        try {
            Portfolio portfolio = null;
            Optional<Portfolio> byId = portfolioRepository.findById(id);
            if(byId.isPresent()){
                portfolio = byId.get();
            }
            return portfolio;
        } catch (Exception exception) {
            logger.error(exception.getMessage(),exception);
            throw new ServiceException(exception);
        }
    }

    @Override
    public Portfolio updatePortfolio(String id,UpdatePortfolioRequest request) throws ServiceException {
        try {
            Portfolio portfolio = null;
            Optional<Portfolio> byId = portfolioRepository.findById(id);
            if(byId.isPresent()){
                portfolio = byId.get();
            }
            if(portfolio!=null){
                portfolio.setName(request.getPortfolioName());
                portfolioRepository.save(portfolio);
            }else{
                throw new ServiceException("Equity with id "+id+" not found");
            }
            return portfolio;
        } catch (ServiceException e) {
            throw e;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Portfolio deletePortfolio(String id) throws ServiceException {
        try {
            Portfolio portfolio = null;
            Optional<Portfolio> byId = portfolioRepository.findById(id);
            if(byId.isPresent()){
                portfolio = byId.get();
            }
            if(portfolio!=null){
                 portfolioRepository.deleteById(id);
            }else{
                throw new ServiceException("Equity with id "+id+" not found");
            }
            return portfolio;
        } catch (ServiceException e) {
            throw e;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }
}
