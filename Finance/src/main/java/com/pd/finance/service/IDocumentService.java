package com.pd.finance.service;

import org.jsoup.nodes.Document;

public interface IDocumentService {

    Document getDocument(String url) throws Exception;
}
