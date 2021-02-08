package com.pd.finance.service;

import com.pd.finance.htmlscrapper.marketgainer.MarketGainerEquityFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DocumentService implements IDocumentService{
    private static final Logger logger = LoggerFactory.getLogger(DocumentService.class);
    private final ConcurrentHashMap<String,Document> documentCache;

    public DocumentService() {
        documentCache = new ConcurrentHashMap<>();
    }

    @Override
    public Document getDocument(String url) throws Exception {
        logger.info("getDocument started executing for url {}",url);
        Document document = documentCache.computeIfAbsent(url,anUrl-> doGetDocument(anUrl));
        logger.info("getDocument completed executing for url {}",url);
        return document;
    }

    private Document doGetDocument(String anUrl) {
        try {
            logger.warn("getDocument dint find in cache , fetching from source for url {}",anUrl);
            return Jsoup.connect(anUrl).get();
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
            return null;
        }
    }
}
