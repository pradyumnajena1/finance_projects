
package com.pd.finance.response.summary;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "maxAge",
    "earningsChart",
    "financialsChart",
    "financialCurrency"
})
@Generated("jsonschema2pojo")
public class Earnings {

    @JsonProperty("maxAge")
    private Long maxAge;
    @JsonProperty("earningsChart")
    private EarningsChart earningsChart;
    @JsonProperty("financialsChart")
    private FinancialsChart financialsChart;
    @JsonProperty("financialCurrency")
    private String financialCurrency;

    @JsonProperty("maxAge")
    public Long getMaxAge() {
        return maxAge;
    }

    @JsonProperty("maxAge")
    public void setMaxAge(Long maxAge) {
        this.maxAge = maxAge;
    }

    @JsonProperty("earningsChart")
    public EarningsChart getEarningsChart() {
        return earningsChart;
    }

    @JsonProperty("earningsChart")
    public void setEarningsChart(EarningsChart earningsChart) {
        this.earningsChart = earningsChart;
    }

    @JsonProperty("financialsChart")
    public FinancialsChart getFinancialsChart() {
        return financialsChart;
    }

    @JsonProperty("financialsChart")
    public void setFinancialsChart(FinancialsChart financialsChart) {
        this.financialsChart = financialsChart;
    }

    @JsonProperty("financialCurrency")
    public String getFinancialCurrency() {
        return financialCurrency;
    }

    @JsonProperty("financialCurrency")
    public void setFinancialCurrency(String financialCurrency) {
        this.financialCurrency = financialCurrency;
    }


}
