package com.pd.finance.service.equityattribute;

import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.htmlscrapper.equity.IEquityEssentialsFactory;
import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityEssentials;
import com.pd.finance.model.EquityIdentifier;
import com.pd.finance.service.IDocumentService;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EquityEssentialsAttributeService extends HtmlScrapperEquityAttributeService {

    private static final Logger logger = LoggerFactory.getLogger(EquityEssentialsAttributeService.class);

    @Autowired
    private IEquityEssentialsFactory equityEssentialsFactory;

    @Autowired
    public EquityEssentialsAttributeService(IDocumentService documentService) {
        super(documentService);
    }

    @Override
    public void enrichEquity(EquityIdentifier identifier, Equity equity) throws ServiceException {
        logger.info( "enrichEquity started for equity: "+ equity.getDefaultEquityIdentifier());
        try {
            Document document = getDocument(identifier);
            EquityEssentials equityEssentials = equityEssentialsFactory.create(document);
            equity.setEssentials(equityEssentials);
            logger.info( "enrichEquity completed for equity: "+ equity.getDefaultEquityIdentifier());
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }


}
