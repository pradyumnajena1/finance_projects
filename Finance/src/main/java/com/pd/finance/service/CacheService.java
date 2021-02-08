package com.pd.finance.service;

import com.pd.finance.model.Equity;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
@Component
public class CacheService implements ICacheService{

    private final ConcurrentHashMap<String,Document> documentCache;
    private final ConcurrentHashMap<Equity,Equity> enrichedEquityCache;
    public CacheService( ) {
        this.documentCache = new ConcurrentHashMap<>();
        this.enrichedEquityCache = new ConcurrentHashMap<>();
    }


    @Override
    public Document getDocument(String url, Function<String, Document> function) {
        return documentCache.computeIfAbsent(url,function);
    }

    @Override
    public Equity getEnrichedEquity(Equity equity, Function<Equity, Equity> function) {
        return enrichedEquityCache.computeIfAbsent(equity,function);
    }

    @Override
    public void clearDocumentCache() {
           documentCache.clear();
    }

    @Override
    public void clearEnrichedEquityCache() {
          enrichedEquityCache.clear();
    }
}
