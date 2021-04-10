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
        "date",
        "actual",
        "estimate"
})
public class EarningsChartLineItem {


    @JsonProperty("date")
    private String date;
    @JsonProperty("actual")
    private Actual actual;
    @JsonProperty("estimate")
    private Estimate estimate;

    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
    }

    @JsonProperty("actual")
    public Actual getActual() {
        return actual;
    }

    @JsonProperty("actual")
    public void setActual(Actual actual) {
        this.actual = actual;
    }

    @JsonProperty("estimate")
    public Estimate getEstimate() {
        return estimate;
    }

    @JsonProperty("estimate")
    public void setEstimate(Estimate estimate) {
        this.estimate = estimate;
    }


}
