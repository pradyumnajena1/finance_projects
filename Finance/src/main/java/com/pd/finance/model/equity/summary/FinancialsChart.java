package com.pd.finance.model.equity.summary;

import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.ArrayList;
import java.util.List;

public class FinancialsChart {

    private List<FinancialChartLineItem> yearly = new ArrayList<FinancialChartLineItem>();


    private List<FinancialChartLineItem> quarterly = new ArrayList<FinancialChartLineItem>();


    public List<FinancialChartLineItem> getYearly() {
        return yearly;
    }


    public void setYearly(List<FinancialChartLineItem> yearly) {
        this.yearly = yearly;
    }


    public List<FinancialChartLineItem> getQuarterly() {
        return quarterly;
    }


    public void setQuarterly(List<FinancialChartLineItem> quarterly) {
        this.quarterly = quarterly;
    }

}
