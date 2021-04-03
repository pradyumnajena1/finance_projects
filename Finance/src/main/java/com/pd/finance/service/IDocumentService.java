package com.pd.finance.service;

import com.pd.finance.model.EquityIdentifier;
import com.pd.finance.model.WebDocument;
import org.jsoup.nodes.Document;

import java.time.temporal.TemporalAmount;

public interface IDocumentService {

    Document getDocument(String url, TemporalAmount ttl) throws Exception;

    WebDocument getWebDocument(String url, TemporalAmount ttl);

    Document getDocument(EquityIdentifier identifier, TemporalAmount ttl) throws Exception;
}
