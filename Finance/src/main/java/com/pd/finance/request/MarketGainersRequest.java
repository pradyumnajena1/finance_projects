package com.pd.finance.request;

import java.math.BigDecimal;

public class MarketGainersRequest {

    private PerformanceFilter performanceFilter;
    private SwotFilter swotFilter;
    private BigDecimal maximumPe;
    private BigDecimal minimumMarketCap;

    public SwotFilter getSwotFilter() {
        return swotFilter;
    }

    public void setSwotFilter(SwotFilter swotFilter) {
        this.swotFilter = swotFilter;
    }

    public PerformanceFilter getPerformanceFilter() {
        return performanceFilter;
    }

    public void setPerformanceFilter(PerformanceFilter performanceFilter) {
        this.performanceFilter = performanceFilter;
    }

    public BigDecimal getMaximumPe() {
        return maximumPe;
    }

    public void setMaximumPe(BigDecimal maximumPe) {
        this.maximumPe = maximumPe;
    }

    public BigDecimal getMinimumMarketCap() {
        return minimumMarketCap;
    }

    public void setMinimumMarketCap(BigDecimal minimumMarketCap) {
        this.minimumMarketCap = minimumMarketCap;
    }
}
