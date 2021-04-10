
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
    "trend",
    "maxAge"
})
@Generated("jsonschema2pojo")
public class RecommendationTrend {

    @JsonProperty("trend")
    private List<Trend> trend = new ArrayList<Trend>();
    @JsonProperty("maxAge")
    private Long maxAge;


    @JsonProperty("trend")
    public List<Trend> getTrend() {
        return trend;
    }

    @JsonProperty("trend")
    public void setTrend(List<Trend> trend) {
        this.trend = trend;
    }

    @JsonProperty("maxAge")
    public Long getMaxAge() {
        return maxAge;
    }

    @JsonProperty("maxAge")
    public void setMaxAge(Long maxAge) {
        this.maxAge = maxAge;
    }


}
