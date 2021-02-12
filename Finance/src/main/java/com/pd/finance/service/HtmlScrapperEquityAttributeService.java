package com.pd.finance.service;

import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityIdentifier;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class HtmlScrapperEquityAttributeService extends AbstractEquityAttributeService{
    private static final Logger logger = LoggerFactory.getLogger(EquityEssentialsAttributeService.class);

    protected IDocumentService documentService;

    public HtmlScrapperEquityAttributeService(IDocumentService documentService) {
        this.documentService = documentService;
    }



    protected   Document  getDocument(EquityIdentifier identifier) throws Exception {
       return documentService.getDocument(identifier);
    }
}
