
package com.pd.finance.response.summary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    "quarterly",
    "earningsDate"
})
@Generated("jsonschema2pojo")
public class EarningsChart {

    @JsonProperty("quarterly")
    private List<EarningsChartLineItem> quarterly = new ArrayList<EarningsChartLineItem>();


    @JsonProperty("quarterly")
    public List<EarningsChartLineItem> getQuarterly() {
        return quarterly;
    }

    @JsonProperty("quarterly")
    public void setQuarterly(List<EarningsChartLineItem> quarterly) {
        this.quarterly = quarterly;
    }



}
