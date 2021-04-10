package com.pd.finance.service;

import com.pd.finance.config.ApplicationConfig;
import com.pd.finance.exceptions.PersistenceException;
import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityIdentifier;
import com.pd.finance.service.equityattribute.EquityHistoricalStockPriceAttributeService;
import com.pd.finance.service.equityattribute.EquitySummaryAttributeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class EquitySummaryServiceService implements IEquitySummaryServiceService {
    private static final Logger logger = LoggerFactory.getLogger(EquitySummaryServiceService.class);


    @Autowired
    private ApplicationConfig config;
    @Autowired
    private IEquityService equityService;

    @Autowired
    private EquitySummaryAttributeService summaryAttributeService;


    @Override
    public void updateAllEquitySummary(String exchange) throws ServiceException {
        try {
            AtomicInteger successfulUpdates = new AtomicInteger(0);

            AtomicInteger currentEquityNumber = new AtomicInteger(0);

            PageRequest pageRequest = PageRequest.of(0, 100);
            Page<Equity> equitiesPage = equityService.getEquities(exchange, pageRequest);

            final int numEquitiesToUpdate = (int) equitiesPage.getTotalElements();
            final int totalPages = equitiesPage.getTotalPages();

            logger.info("updateEquities No of equities to update {} no of pages {}", numEquitiesToUpdate, totalPages);
            while (!equitiesPage.isEmpty()) {

                int currentPageNumber = equitiesPage.getNumber();
                logger.info("updateEquities process started pageNumber {} out of {} pages  ", currentPageNumber, totalPages);

                List<Equity> equities = equitiesPage.getContent();
                logger.info("updateEquities  pageNumber {} numEquities {} ", currentPageNumber, equities.size());

                equities.parallelStream().forEach(anEquity -> {
                    try {
                        currentEquityNumber.incrementAndGet();
                        updateEquitySummary(anEquity);
                        successfulUpdates.incrementAndGet();

                        logger.info("successfully updated summary of equity {}", anEquity.getDefaultEquityIdentifier());
                        logger.info("updateEquities currentEquityNumber:{} successfulUpdates:{} numEquitiesToUpdate:{} pageNumber:{} totalPages:{}", currentEquityNumber, successfulUpdates, numEquitiesToUpdate, currentPageNumber, totalPages);

                    } catch (ServiceException e) {
                        logger.info("failed to update summary of equity {}", anEquity.getDefaultEquityIdentifier());
                    }

                });


                pageRequest = (PageRequest) pageRequest.next();
                equitiesPage = equityService.getEquities(exchange, pageRequest);
            }


        } catch (PersistenceException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Equity updateEquitySummary(Equity equity) throws ServiceException {
        try {

            EquityIdentifier identifier = equity.getDefaultEquityIdentifier();
            summaryAttributeService.enrichEquity(identifier, equity);
            equityService.upsertEquity(equity);
            logger.info("successfully updated summary of equity {}", identifier);
            return equity;

        } catch (ServiceException | PersistenceException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
