package com.pd.finance.model.equity.summary;

import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.ArrayList;
import java.util.List;

public class EarningsChart {

    @JsonProperty("quarterly")
    private List<EarningsChartLineItem> quarterly = new ArrayList<EarningsChartLineItem>();

    public List<EarningsChartLineItem> getQuarterly() {
        return quarterly;
    }

    public void setQuarterly(List<EarningsChartLineItem> quarterly) {
        this.quarterly = quarterly;
    }
}
