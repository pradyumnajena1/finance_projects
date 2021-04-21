package com.pd.finance.service.portfolio;

import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.exceptions.UserNotFoundException;
import com.pd.finance.model.UserEquityQuery;
import com.pd.finance.model.portfolio.Portfolio;
import com.pd.finance.persistence.PortfolioRepository;
import com.pd.finance.persistence.UserRepository;
import com.pd.finance.request.CreatePortfolioRequest;
import com.pd.finance.request.UpdatePortfolioRequest;
import com.pd.finance.service.EquityService;
import com.pd.finance.service.SequenceGeneratorService;
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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;
    @Override
    public Portfolio createPortfolio(Long userId,CreatePortfolioRequest request) throws ServiceException {
        try {
            logger.info("createPortfolio exec started for userId:{}",userId );
            if(userRepository.existsById(userId)){
                Portfolio portfolio = new Portfolio();
                long id = sequenceGeneratorService.generateSequence(Portfolio.SEQUENCE_NAME);
                portfolio.setId(id);
                portfolio.setName(request.getPortfolioName());
                portfolio.setUserId(userId);
                portfolio.setCreatedDate(new Date());
                portfolio.setUpdatedDate(new Date());
                Portfolio createdPortfolio = portfolioRepository.save(portfolio);
                logger.info("createPortfolio exec completed for userId:{}",userId );
                return createdPortfolio;
            }else {
                throw new UserNotFoundException("User with id:"+userId+" doesn't exist");
            }

        } catch (Exception exception) {

            logger.error(exception.getMessage(),exception);
            throw new ServiceException(exception.getMessage(),exception);
        }
    }

    @Override
    public Portfolio getPortfolio(Long userId,Long id) throws ServiceException {
        try {
            logger.info("getPortfolio exec started for userId:{} portfolioId:{}",userId,id);
            if (userRepository.existsById(userId)) {
                Portfolio portfolio = null;
                Optional<Portfolio> byId = portfolioRepository.findById(id);
                if(byId.isPresent()){
                    portfolio = byId.get();
                }
                logger.info("getPortfolio exec completed for userId:{} portfolioId:{}",userId,id);
                return portfolio;
            }else {
                throw new UserNotFoundException("User with id:"+userId+" doesn't exist");
            }
        } catch (Exception exception) {
            logger.error(exception.getMessage(),exception);
            throw new ServiceException(exception);
        }
    }

    @Override
    public Portfolio updatePortfolio(Long userId,Long id,UpdatePortfolioRequest request) throws ServiceException {
        try {
            logger.info("updatePortfolio exec started for userId:{} portfolioId:{}",userId,id);
            if (userRepository.existsById(userId)) {
                Portfolio portfolio = null;
                Optional<Portfolio> byId = portfolioRepository.findById(id);
                if(byId.isPresent()){
                    portfolio = byId.get();
                }
                if(portfolio!=null){
                    portfolio.setName(request.getPortfolioName());
                    portfolio.setUpdatedDate(new Date());
                    portfolioRepository.save(portfolio);
                }else{
                    throw new ServiceException("Equity with id "+id+" not found");
                }
                logger.info("updatePortfolio exec completed for userId:{} portfolioId:{}",userId,id);
                return portfolio;
            }else {
                throw new UserNotFoundException("User with id:"+userId+" doesn't exist");
            }
        } catch (ServiceException e) {
            throw e;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Portfolio deletePortfolio(Long userId,Long id) throws ServiceException {
        try {
            logger.info("deletePortfolio exec started for userId:{} portfolioId:{}",userId,id);
            if (userRepository.existsById(userId)) {
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
                logger.info("deletePortfolio exec completed for userId:{} portfolioId:{}",userId,id);
                return portfolio;
            }else {
                throw new UserNotFoundException("User with id:"+userId+" doesn't exist");
            }
        } catch (ServiceException e) {
            throw e;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }
}
