package com.pd.finance.service.equityattribute;

import com.pd.finance.model.EquityIdentifier;
import com.pd.finance.service.IDocumentService;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Period;

public abstract class HtmlScrapperEquityAttributeService extends AbstractEquityAttributeService {
    private static final Logger logger = LoggerFactory.getLogger(EquityEssentialsAttributeService.class);

    protected IDocumentService documentService;

    public HtmlScrapperEquityAttributeService(IDocumentService documentService) {
        this.documentService = documentService;
    }



    protected   Document  getDocument(EquityIdentifier identifier) throws Exception {
       return documentService.getDocument(identifier, Period.ofDays(1));
    }
}
