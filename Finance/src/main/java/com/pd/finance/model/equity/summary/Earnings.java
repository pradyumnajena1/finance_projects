package com.pd.finance.model.equity.summary;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;


public class Earnings {
    @JsonProperty("maxAge")
    @Field(targetType = FieldType.INT64)
    private Long maxAge;

    @JsonProperty("earningsChart")
    private EarningsChart earningsChart;
    @JsonProperty("financialsChart")
    private FinancialsChart financialsChart;
    @JsonProperty("financialCurrency")
    private String financialCurrency;

    public Long getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Long maxAge) {
        this.maxAge = maxAge;
    }

    public EarningsChart getEarningsChart() {
        return earningsChart;
    }

    public void setEarningsChart(EarningsChart earningsChart) {
        this.earningsChart = earningsChart;
    }

    public FinancialsChart getFinancialsChart() {
        return financialsChart;
    }

    public void setFinancialsChart(FinancialsChart financialsChart) {
        this.financialsChart = financialsChart;
    }

    public String getFinancialCurrency() {
        return financialCurrency;
    }

    public void setFinancialCurrency(String financialCurrency) {
        this.financialCurrency = financialCurrency;
    }
}
