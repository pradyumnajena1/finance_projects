package com.pd.finance.service;

import com.pd.finance.model.CacheStatistics;
import com.pd.finance.model.Equity;
import org.jsoup.nodes.Document;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public interface ICacheService {



    public Equity getEquity(String equityId, Function<String, Equity> function);
    public  Document getDocument(String url, Function<String,Document> function);
    public  Equity getEnrichedEquity(Equity equity, Function<Equity,Equity> function);

    CacheStatistics getCacheStatistics();

    public void clearDocumentCache();
    public void clearEnrichedEquityCache();

    void clearEquityCache();
}
