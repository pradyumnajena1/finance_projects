
package com.pd.finance.response.summary;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "date",
    "revenue",
    "earnings"
})
@Generated("jsonschema2pojo")
public class FinancialChartLineItem {

    @JsonProperty("date")
    private String date;
    @JsonProperty("revenue")
    private FinancialChartLineItemRevenue financialChartLineItemRevenue;
    @JsonProperty("earnings")
    private FinancialChartLineItemEarnings earnings;

    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
    }

    @JsonProperty("revenue")
    public FinancialChartLineItemRevenue getRevenue() {
        return financialChartLineItemRevenue;
    }

    @JsonProperty("revenue")
    public void setRevenue(FinancialChartLineItemRevenue yearlyRevenue) {
        this.financialChartLineItemRevenue = yearlyRevenue;
    }

    @JsonProperty("earnings")
    public FinancialChartLineItemEarnings getEarnings() {
        return earnings;
    }

    @JsonProperty("earnings")
    public void setEarnings(FinancialChartLineItemEarnings earnings) {
        this.earnings = earnings;
    }


}
