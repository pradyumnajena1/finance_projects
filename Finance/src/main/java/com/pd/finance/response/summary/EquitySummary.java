
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
    "recommendationTrend",
    "earnings",
    "defaultKeyStatistics",
    "summaryProfile",
    "financialData"
})
@Generated("jsonschema2pojo")
public class EquitySummary {

    @JsonProperty("recommendationTrend")
    private RecommendationTrend recommendationTrend;

    @JsonProperty("earnings")
    private Earnings earnings;

    @JsonProperty("defaultKeyStatistics")
    private DefaultKeyStatistics defaultKeyStatistics;

    @JsonProperty("summaryProfile")
    private SummaryProfile summaryProfile;

    @JsonProperty("financialData")
    private FinancialData financialData;



    @JsonProperty("recommendationTrend")
    public RecommendationTrend getRecommendationTrend() {
        return recommendationTrend;
    }

    @JsonProperty("recommendationTrend")
    public void setRecommendationTrend(RecommendationTrend recommendationTrend) {
        this.recommendationTrend = recommendationTrend;
    }

    @JsonProperty("earnings")
    public Earnings getEarnings() {
        return earnings;
    }

    @JsonProperty("earnings")
    public void setEarnings(Earnings earnings) {
        this.earnings = earnings;
    }

    @JsonProperty("defaultKeyStatistics")
    public DefaultKeyStatistics getDefaultKeyStatistics() {
        return defaultKeyStatistics;
    }

    @JsonProperty("defaultKeyStatistics")
    public void setDefaultKeyStatistics(DefaultKeyStatistics defaultKeyStatistics) {
        this.defaultKeyStatistics = defaultKeyStatistics;
    }

    @JsonProperty("summaryProfile")
    public SummaryProfile getSummaryProfile() {
        return summaryProfile;
    }

    @JsonProperty("summaryProfile")
    public void setSummaryProfile(SummaryProfile summaryProfile) {
        this.summaryProfile = summaryProfile;
    }

    @JsonProperty("financialData")
    public FinancialData getFinancialData() {
        return financialData;
    }

    @JsonProperty("financialData")
    public void setFinancialData(FinancialData financialData) {
        this.financialData = financialData;
    }


}
