package com.pd.finance.service;

import com.pd.finance.model.CacheStatistics;
import com.pd.finance.model.Equity;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
@Service
public class CacheService implements ICacheService{

    private final ConcurrentHashMap<String,Document> documentCache;
    private final ConcurrentHashMap<Equity,Equity> enrichedEquityCache;
    private final ConcurrentHashMap<String,Equity> equityCache;




    public CacheService( ) {
        this.documentCache = new ConcurrentHashMap<>();
        this.enrichedEquityCache = new ConcurrentHashMap<>();
        this.equityCache = new ConcurrentHashMap<>();

    }

    @Override
    public Equity getEquity(String equityId, Function<String, Equity> function) {
        Equity equity = equityCache.computeIfAbsent(equityId, function);

        return equity;
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
    public CacheStatistics getCacheStatistics(){
         CacheStatistics statistics = new CacheStatistics();
         statistics.getStat().put("documentCache",documentCache.size());
        statistics.getStat().put("equityCache",equityCache.size());
        statistics.getStat().put("enrichedEquityCache",enrichedEquityCache.size());
        return statistics;
    }

    @Override
    public void clearDocumentCache() {
           documentCache.clear();
    }

    @Override
    public void clearEnrichedEquityCache() {
          enrichedEquityCache.clear();
    }

    @Override
    public void clearEquityCache() {
        equityCache.clear();
    }
}
