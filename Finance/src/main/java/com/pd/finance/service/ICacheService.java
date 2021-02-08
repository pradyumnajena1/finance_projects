package com.pd.finance.service;

import com.pd.finance.model.Equity;
import org.jsoup.nodes.Document;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public interface ICacheService {

    public  Document getDocument(String url, Function<String,Document> function);
    public  Equity getEnrichedEquity(Equity equity, Function<Equity,Equity> function);

    public void clearDocumentCache();
    public void clearEnrichedEquityCache();
}
