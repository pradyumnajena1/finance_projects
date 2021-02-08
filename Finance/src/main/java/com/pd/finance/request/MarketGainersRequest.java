package com.pd.finance.request;

import java.math.BigDecimal;

public class MarketGainersRequest {

    private PerformanceFilter performanceFilter;
    private SwotFilter swotFilter;
    private OverviewFilter overviewFilter;
    private DebugFilter debugFilter = new DebugFilter();

    public DebugFilter getDebugFilter() {
        return debugFilter;
    }

    public void setDebugFilter(DebugFilter debugFilter) {
        this.debugFilter = debugFilter;
    }

    public OverviewFilter getOverviewFilter() {
        return overviewFilter;
    }

    public void setOverviewFilter(OverviewFilter overviewFilter) {
        this.overviewFilter = overviewFilter;
    }

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


}
