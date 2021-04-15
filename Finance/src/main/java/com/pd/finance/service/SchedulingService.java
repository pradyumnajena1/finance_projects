package com.pd.finance.service;


import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.filter.db.EquityUpdateDateFilter;
import com.pd.finance.request.DebugFilter;
import com.pd.finance.request.EquityBulkUpdateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.Period;
import java.util.Date;

@Service
public class SchedulingService {

    private static final Logger logger = LoggerFactory.getLogger(SchedulingService.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    public static final int NUM_EQUITIES_TO_UPDATE = 100;

    @Autowired
    private IWebDocumentService webDocumentService;
    @Autowired
    private IEquityService equityService;

    @Scheduled(fixedRate = 1000*60*60)
    public void reportCurrentTime() {
        logger.info("The time is now {}", dateFormat.format(new Date()));
    }

    @Scheduled(fixedRate = 1000*60*60)
    public void removeExpiredWebDocuments() {

        try {
            logger.info("removeExpiredWebDocuments exec started ");
            webDocumentService.removeExpiredWebDocuments();

            logger.info("removeExpiredWebDocuments exec completed ");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);

        }
    }


    @Scheduled(fixedRate = 1000*60*60)
    public void updateEquities() {

        try {
            logger.info("updateEquities exec started by scheduling service");
           doUpdateEquities();

            logger.info("updateEquities exec started by scheduling service completed ");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);

        }
    }

    protected void doUpdateEquities() throws ServiceException {
        EquityBulkUpdateRequest request = new EquityBulkUpdateRequest();
        EquityUpdateDateFilter updateDateFilter = new EquityUpdateDateFilter();
        updateDateFilter.setUpdatedBefore(new Date(Instant.now().minus(Period.ofDays(1)).toEpochMilli()));
        request.setUpdateDateFilter(updateDateFilter);
        DebugFilter debugFilter = new DebugFilter();
        debugFilter.setNumEquities(NUM_EQUITIES_TO_UPDATE);
        request.setDebugFilter(debugFilter);
        equityService.updateEquities(request);
    }
}
