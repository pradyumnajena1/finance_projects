package com.pd.finance.request;

import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

public class MarketGainersWebCrawlRequest {

    private DebugFilter debugFilter = new DebugFilter();

    public DebugFilter getDebugFilter() {
        return debugFilter;
    }

    public void setDebugFilter(DebugFilter debugFilter) {
        this.debugFilter = debugFilter;
    }
}
