package com.pd.finance.request;

public class ClearCacheRequest {


    private boolean clearEnrichedEquityCache = false;
    private boolean clearEquityCache = false;




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
