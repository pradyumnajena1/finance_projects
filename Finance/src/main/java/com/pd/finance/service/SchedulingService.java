package com.pd.finance.service;


import com.pd.finance.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class SchedulingService {

    private static final Logger logger = LoggerFactory.getLogger(SchedulingService.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    IWebDocumentService webDocumentService;

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
        } catch (ServiceException e) {
            logger.error(e.getMessage(),e);

        }
    }
}
