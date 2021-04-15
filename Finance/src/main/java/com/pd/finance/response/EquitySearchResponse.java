package com.pd.finance.response;

import com.pd.finance.model.Equity;

import java.util.List;

public class EquitySearchResponse {
    private List<Equity> equities;

    public List<Equity> getEquities() {
        return equities;
    }

    public void setEquities(List<Equity> equities) {
        this.equities = equities;
    }
}
