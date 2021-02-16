package com.pd.finance.request;

public class AbstractWebCrawlRequest {

    protected DebugFilter debugFilter = new DebugFilter();

    public DebugFilter getDebugFilter() {
        return debugFilter;
    }

    public void setDebugFilter(DebugFilter debugFilter) {
        this.debugFilter = debugFilter;
    }
}
