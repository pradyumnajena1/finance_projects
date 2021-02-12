package com.pd.finance.request;

public class ClearCacheRequest {

    private boolean clearDocumentCache = false;
    private boolean clearEnrichedEquityCache = false;
    private boolean clearEquityCache = false;

    public boolean isClearDocumentCache() {
        return clearDocumentCache;
    }

    public void setClearDocumentCache(boolean clearDocumentCache) {
        this.clearDocumentCache = clearDocumentCache;
    }

    public boolean isClearEnrichedEquityCache() {
        return clearEnrichedEquityCache;
    }

    public void setClearEnrichedEquityCache(boolean clearEnrichedEquityCache) {
        this.clearEnrichedEquityCache = clearEnrichedEquityCache;
    }

    public boolean isClearEquityCache() {
        return clearEquityCache;
    }

    public void setClearEquityCache(boolean clearEquityCache) {
        this.clearEquityCache = clearEquityCache;
    }
}
