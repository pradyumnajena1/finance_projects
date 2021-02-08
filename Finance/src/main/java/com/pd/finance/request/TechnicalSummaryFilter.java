package com.pd.finance.request;

public class TechnicalSummaryFilter {
    private Integer minBullish;
    private Integer maxBearish;

    public Integer getMinBullish() {
        return minBullish;
    }

    public void setMinBullish(Integer minBullish) {
        this.minBullish = minBullish;
    }

    public Integer getMaxBearish() {
        return maxBearish;
    }

    public void setMaxBearish(Integer maxBearish) {
        this.maxBearish = maxBearish;
    }
}
