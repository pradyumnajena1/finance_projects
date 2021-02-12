package com.pd.finance.service;

import com.pd.finance.model.EquityIdentifier;
import org.jsoup.nodes.Document;

public interface IDocumentService {

    Document getDocument(String url) throws Exception;

    Document getDocument(EquityIdentifier identifier) throws Exception;
}
