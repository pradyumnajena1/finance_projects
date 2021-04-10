
package com.pd.finance.response.summary;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "yearly",
    "quarterly"
})
@Generated("jsonschema2pojo")
public class FinancialsChart {

    @JsonProperty("yearly")
    private List<FinancialChartLineItem> yearly = new ArrayList<FinancialChartLineItem>();

    @JsonProperty("quarterly")
    private List<FinancialChartLineItem> quarterly = new ArrayList<FinancialChartLineItem>();

    @JsonProperty("yearly")
    public List<FinancialChartLineItem> getYearly() {
        return yearly;
    }

    @JsonProperty("yearly")
    public void setYearly(List<FinancialChartLineItem> yearly) {
        this.yearly = yearly;
    }

    @JsonProperty("quarterly")
    public List<FinancialChartLineItem> getQuarterly() {
        return quarterly;
    }

    @JsonProperty("quarterly")
    public void setQuarterly(List<FinancialChartLineItem> quarterly) {
        this.quarterly = quarterly;
    }


}
