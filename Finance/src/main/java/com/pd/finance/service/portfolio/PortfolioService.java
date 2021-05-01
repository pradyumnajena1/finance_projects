package com.pd.finance.service.portfolio;

import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.exceptions.UserNotFoundException;
import com.pd.finance.model.UserEquityQuery;
import com.pd.finance.model.portfolio.Portfolio;
import com.pd.finance.model.portfolio.PortfolioEquity;
import com.pd.finance.model.portfolio.PortfolioEquityLot;
import com.pd.finance.persistence.PortfolioRepository;
import com.pd.finance.persistence.UserRepository;
import com.pd.finance.request.*;
import com.pd.finance.service.EquityService;
import com.pd.finance.service.SequenceGeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PortfolioService implements IPortfolioService {
    private static final Logger logger = LoggerFactory.getLogger(PortfolioService.class);


    @Autowired
    private PortfolioRepository portfolioRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Override
    public Portfolio createPortfolio(Long userId, CreatePortfolioRequest request) throws ServiceException {
        try {
            logger.info("createPortfolio exec started for userId:{}", userId);
            if (!userRepository.existsById(userId)) {
                throw new UserNotFoundException("User with id:" + userId + " doesn't exist");
            }

            Portfolio portfolio = new Portfolio();
            long id = sequenceGeneratorService.generateSequence(Portfolio.SEQUENCE_NAME);
            portfolio.setId(id);
            portfolio.setName(request.getPortfolioName());
            portfolio.setUserId(userId);
            portfolio.setCreatedDate(new Date());
            portfolio.setUpdatedDate(new Date());
            Portfolio createdPortfolio = portfolioRepository.save(portfolio);
            logger.info("createPortfolio exec completed for userId:{}", userId);
            return createdPortfolio;


        } catch (Exception exception) {

            logger.error(exception.getMessage(), exception);
            throw new ServiceException(exception.getMessage(), exception);
        }
    }

    @Override
    public Portfolio getPortfolio(Long userId, Long id) throws ServiceException {
        try {
            logger.info("getPortfolio exec started for userId:{} portfolioId:{}", userId, id);
            if (!userRepository.existsById(userId)) {
                throw new UserNotFoundException("User with id:" + userId + " doesn't exist");
            }

            Portfolio portfolio = null;
            Optional<Portfolio> byId = portfolioRepository.findById(id);
            if (byId.isPresent()) {
                portfolio = byId.get();
            }
            logger.info("getPortfolio exec completed for userId:{} portfolioId:{}", userId, id);
            return portfolio;

        } catch (Exception exception) {
            logger.error(exception.getMessage(), exception);
            throw new ServiceException(exception);
        }
    }

    @Override
    public Portfolio updatePortfolio(Long userId, Long id, UpdatePortfolioRequest request) throws ServiceException {
        try {
            logger.info("updatePortfolio exec started for userId:{} portfolioId:{}", userId, id);
            if (!userRepository.existsById(userId)) {
                throw new UserNotFoundException("User with id:" + userId + " doesn't exist");
            }

            Portfolio portfolio = null;
            Optional<Portfolio> byId = portfolioRepository.findById(id);
            if (byId.isPresent()) {
                portfolio = byId.get();
            }
            if (portfolio == null) {
                throw new ServiceException("Equity with id " + id + " not found");
            }

            portfolio.setName(request.getPortfolioName());
            portfolio.setUpdatedDate(new Date());
            portfolioRepository.save(portfolio);

            logger.info("updatePortfolio exec completed for userId:{} portfolioId:{}", userId, id);
            return portfolio;

        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Portfolio deletePortfolio(Long userId, Long id) throws ServiceException {
        try {
            logger.info("deletePortfolio exec started for userId:{} portfolioId:{}", userId, id);
            if (!userRepository.existsById(userId)) {
                throw new UserNotFoundException("User with id:" + userId + " doesn't exist");
            }

            Portfolio portfolio = null;
            Optional<Portfolio> byId = portfolioRepository.findById(id);
            if (byId.isPresent()) {
                portfolio = byId.get();
            }
            if (portfolio == null) {
                throw new ServiceException("Equity with id " + id + " not found");
            }

            portfolioRepository.deleteById(id);

            logger.info("deletePortfolio exec completed for userId:{} portfolioId:{}", userId, id);
            return portfolio;

        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Portfolio addPortfolioEquities(Long userId, Long portfolioId, AddPortfolioEquityRequest request) throws ServiceException {
        try {
            logger.info("addPortfolioEquities exec started for userId:{}", userId);
            if (!userRepository.existsById(userId)) {
                throw new UserNotFoundException("User with id:" + userId + " doesn't exist");
            }
            if (!portfolioRepository.existsById(portfolioId)) {
                throw new ServiceException("Portfolio with id " + portfolioId + " not found");
            }
            Portfolio portfolio = portfolioRepository.findById(portfolioId).get();
            request.getEquityIds().stream().forEach(equityId -> {
                PortfolioEquity portfolioEquity = new PortfolioEquity(portfolioId, equityId);
                if(!portfolio.getPortfolioEquities().contains(portfolioEquity)){
                    portfolio.getPortfolioEquities().add(portfolioEquity);
                }
            });
            portfolio.setUpdatedDate(new Date());
            Portfolio updatedPortfolio = portfolioRepository.save(portfolio);
            logger.info("createPortfolio exec completed for userId:{}", userId);
            return updatedPortfolio;
        } catch (Exception exception) {

            logger.error(exception.getMessage(), exception);
            throw new ServiceException(exception.getMessage(), exception);
        }
    }

    @Override
    public Portfolio deletePortfolioEquities(Long userId, Long portfolioId, DeletePortfolioEquityRequest request) throws ServiceException {
        try {
            logger.info("deletePortfolioEquities exec started for userId:{}", userId);
            if (!userRepository.existsById(userId)) {
                throw new UserNotFoundException("User with id:" + userId + " doesn't exist");
            }
            if (!portfolioRepository.existsById(portfolioId)) {
                throw new ServiceException("Portfolio with id " + portfolioId + " not found");
            }
            Portfolio portfolio = portfolioRepository.findById(portfolioId).get();
            request.getEquityIds().stream().forEach(equityId -> {
                PortfolioEquity portfolioEquity = new PortfolioEquity(portfolioId, equityId);
                if(portfolio.getPortfolioEquities().contains(portfolioEquity)){
                    portfolio.getPortfolioEquities().remove(portfolioEquity);
                }
            });
            portfolio.setUpdatedDate(new Date());
            Portfolio updatedPortfolio = portfolioRepository.save(portfolio);
            logger.info("deletePortfolioEquities exec completed for userId:{}", userId);
            return updatedPortfolio;

        } catch (Exception exception) {

            logger.error(exception.getMessage(), exception);
            throw new ServiceException(exception.getMessage(), exception);
        }
    }

    @Override
    public Portfolio addPortfolioEquityLots(Long userId, Long portfolioId, String portfolioEquityId, AddPortfolioEquityLotsRequest request) throws ServiceException {
        try {
            logger.info("addPortfolioEquities exec started for userId:{}", userId);
            if (!userRepository.existsById(userId)) {
                throw new UserNotFoundException("User with id:" + userId + " doesn't exist");
            }
            if (!portfolioRepository.existsById(portfolioId)) {
                throw new ServiceException("Portfolio with id " + portfolioId + " not found");
            }
            Portfolio portfolio = portfolioRepository.findById(portfolioId).get();
            Optional<PortfolioEquity> any = portfolio.getPortfolioEquities().stream().filter(portfolioEquity -> portfolioEquity.getEquityId().equals(portfolioEquityId)).findAny();
            if(!any.isPresent()){
                throw new ServiceException("Portfolio with id " + portfolioId + " doesn't contain equity "+portfolioEquityId );
            }
            PortfolioEquity portfolioEquity = any.get();
            request.getEquityLots().stream().forEach(portfolioEquityLot -> {
                PortfolioEquityLot equityLot = new PortfolioEquityLot(portfolioId,portfolioEquityId,portfolioEquityLot.getNumUnits(),portfolioEquityLot.getPrice(),portfolioEquityLot.getUnitPurchasedDate());
                portfolioEquity.getLots().add(equityLot);

            });

            portfolio.setUpdatedDate(new Date());
            Portfolio updatedPortfolio = portfolioRepository.save(portfolio);
            logger.info("createPortfolio exec completed for userId:{}", userId);
            return updatedPortfolio;
        } catch (Exception exception) {

            logger.error(exception.getMessage(), exception);
            throw new ServiceException(exception.getMessage(), exception);
        }
    }

    @Override
    public Portfolio deletePortfolioEquityLots(Long userId, Long portfolioId, String portfolioEquityId, DeletePortfolioEquityLotsRequest request) throws ServiceException {
        try {
            logger.info("addPortfolioEquities exec started for userId:{}", userId);
            if (!userRepository.existsById(userId)) {
                throw new UserNotFoundException("User with id:" + userId + " doesn't exist");
            }
            if (!portfolioRepository.existsById(portfolioId)) {
                throw new ServiceException("Portfolio with id " + portfolioId + " not found");
            }
            Portfolio portfolio = portfolioRepository.findById(portfolioId).get();
            Optional<PortfolioEquity> any = portfolio.getPortfolioEquities().stream().filter(portfolioEquity -> portfolioEquity.getEquityId().equals(portfolioEquityId)).findAny();
            if(!any.isPresent()){
                throw new ServiceException("Portfolio with id " + portfolioId + " doesn't contain equity "+portfolioEquityId );
            }
            PortfolioEquity portfolioEquity = any.get();

            request.getLotIds().stream().forEach(lotId -> {
                PortfolioEquityLot equityLot = new PortfolioEquityLot(portfolioId,portfolioEquityId,lotId);
                if(portfolioEquity.getLots().contains(equityLot)){

                    portfolioEquity.getLots().remove(equityLot);
                }else {
                    logger.warn("PortfolioEquity with id " + portfolioEquityId + " doesn't contain lot "+lotId);
                }

            });

            portfolio.setUpdatedDate(new Date());
            Portfolio updatedPortfolio = portfolioRepository.save(portfolio);
            logger.info("createPortfolio exec completed for userId:{}", userId);
            return updatedPortfolio;
        } catch (Exception exception) {

            logger.error(exception.getMessage(), exception);
            throw new ServiceException(exception.getMessage(), exception);
        }
    }
}
