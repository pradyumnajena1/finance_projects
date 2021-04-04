package com.pd.finance.request;

import com.pd.finance.filter.code.EquityNamesFilter;
import com.pd.finance.filter.db.EquityExchangeFilter;

public class EquityBulkUpdateRequest {

    private EquityExchangeFilter exchangeFilter;
    private EquityNamesFilter  namesFilter;

    public EquityBulkUpdateRequest() {
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
}
