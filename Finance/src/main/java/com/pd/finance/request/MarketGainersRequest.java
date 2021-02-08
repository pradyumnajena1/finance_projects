package com.pd.finance.request;

import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

public class MarketGainersRequest {

    private PerformanceFilter performanceFilter;
    private SwotFilter swotFilter;
    private OverviewFilter overviewFilter;
    private DebugFilter debugFilter = new DebugFilter();
    private TechnicalPeriodFilter technicalPeriodFilter;

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

    public TechnicalPeriodFilter getTechnicalPeriodFilter() {
        return technicalPeriodFilter;
    }

    public void setTechnicalPeriodFilter(TechnicalPeriodFilter technicalPeriodFilter) {
        this.technicalPeriodFilter = technicalPeriodFilter;
    }

    public List<FieldError> validate(){
        List<FieldError> errors = new ArrayList<>();
        if(technicalPeriodFilter !=null){
            errors.addAll(technicalPeriodFilter.validate());
        }
        return  errors;
    }
}
