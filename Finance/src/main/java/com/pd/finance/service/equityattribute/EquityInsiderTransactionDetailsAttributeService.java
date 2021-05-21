package com.pd.finance.service.equityattribute;

import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.htmlscrapper.equity.IEquityDealsDetailsFactory;
import com.pd.finance.htmlscrapper.equity.IEquityInsiderTransactionDetailsFactory;
import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityIdentifier;
import com.pd.finance.service.IDocumentService;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EquityInsiderTransactionDetailsAttributeService extends HtmlScrapperEquityAttributeService implements IEquityAttributeService{
    private static final Logger logger = LoggerFactory.getLogger(EquityInsiderTransactionDetailsAttributeService.class);

    @Autowired
    private IEquityInsiderTransactionDetailsFactory insiderTransactionDetailsFactory;
    @Autowired
    public EquityInsiderTransactionDetailsAttributeService(IDocumentService documentService) {
        super(documentService);
    }


    @Override
    public void enrichEquity(EquityIdentifier identifier, Equity equity) throws ServiceException {
        logger.info( "enrichEquity completed for equity: "+ equity.getDefaultEquityIdentifier());
        try {


            Document document = getDocument(identifier);
            equity.setInsiderTransactionDetails(insiderTransactionDetailsFactory.create(document));
            logger.info( "enrichEquity completed for equity: "+ equity.getDefaultEquityIdentifier());
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }
}
