package com.pd.finance.service;

import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.htmlscrapper.equity.IEquitySwotFactory;
import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityIdentifier;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EquitySwotAttributeService extends HtmlScrapperEquityAttributeService{
    private static final Logger logger = LoggerFactory.getLogger(EquitySwotAttributeService.class);

    @Autowired
    private IEquitySwotFactory equitySwotFactory;

    @Autowired
    public EquitySwotAttributeService(IDocumentService documentService) {
        super(documentService);
    }

    @Override
    public void enrichEquity(EquityIdentifier identifier, Equity equity) throws ServiceException {
        logger.info( "enrichEquity started for equity: "+ equity.getEquityIdentifiers());
        try {
            Document document = getDocument(identifier);
            equity.setSwotDetails(equitySwotFactory.create(document));
            logger.info( "enrichEquity completed for equity: "+ equity.getEquityIdentifiers());
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }
}
