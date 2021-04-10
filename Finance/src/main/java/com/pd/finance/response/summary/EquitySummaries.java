package com.pd.finance.response.summary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "result"
})
public class EquitySummaries {

    @JsonProperty("result")
    private List<EquitySummary> summaries;

    public List<EquitySummary> getSummaries() {
        return summaries;
    }

    public void setSummaries(List<EquitySummary> summaries) {
        this.summaries = summaries;
    }
}
