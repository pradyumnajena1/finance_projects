package com.pd.finance.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pd.finance.filter.code.EquityNamesFilter;
import com.pd.finance.filter.db.EquityExchangeFilter;
import com.pd.finance.filter.db.EquityUpdateDateFilter;

public class EquityBulkUpdateRequest {

    @JsonProperty("exchangeFilter")
    private EquityExchangeFilter exchangeFilter;
    @JsonProperty("namesFilter")
    private EquityNamesFilter  namesFilter;
    @JsonProperty("updateDateFilter")
    private EquityUpdateDateFilter updateDateFilter;
    @JsonProperty("debugFilter")
    private DebugFilter debugFilter;

    public EquityBulkUpdateRequest() {
    }

    public DebugFilter getDebugFilter() {
        return debugFilter;
    }

    public void setDebugFilter(DebugFilter debugFilter) {
        this.debugFilter = debugFilter;
    }

    public EquityExchangeFilter getExchangeFilter() {
        return exchangeFilter;
    }

    public void setExchangeFilter(EquityExchangeFilter exchangeFilter) {
        this.exchangeFilter = exchangeFilter;
    }

    public EquityNamesFilter getNamesFilter() {
        return namesFilter;
    }

    public void setNamesFilter(EquityNamesFilter namesFilter) {
        this.namesFilter = namesFilter;
    }

    public EquityUpdateDateFilter getUpdateDateFilter() {
        return updateDateFilter;
    }

    public void setUpdateDateFilter(EquityUpdateDateFilter updateDateFilter) {
        this.updateDateFilter = updateDateFilter;
    }
}
