package com.pd.finance.service;

import com.pd.finance.exceptions.PersistenceException;
import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.persistence.WebDocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebDocumentService implements IWebDocumentService {
    private static final Logger logger = LoggerFactory.getLogger(WebDocumentService.class);

    @Autowired
    private WebDocumentRepository documentRepository;

    @Override
    public void removeExpiredWebDocuments() throws ServiceException{
        logger.info("removeExpiredWebDocuments exec started");
        try {
            documentRepository.removeExpiredWebDocuments();
            logger.info("removeExpiredWebDocuments exec completed");
        } catch (PersistenceException e) {
           logger.error("removeExpiredWebDocuments exec failed "+e.getMessage(),e);
           throw new ServiceException(e);
        }
    }


}
