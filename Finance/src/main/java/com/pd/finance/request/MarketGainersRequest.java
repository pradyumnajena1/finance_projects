package com.pd.finance.request;

import java.math.BigDecimal;

public class MarketGainersRequest {

    private PerformanceFilter performanceFilter;
    private SwotFilter swotFilter;
    private OverviewFilter overviewFilter;

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
