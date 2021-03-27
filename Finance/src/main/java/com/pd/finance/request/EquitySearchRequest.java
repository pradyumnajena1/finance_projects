package com.pd.finance.request;

import com.pd.finance.filter.*;

import com.pd.finance.filter.code.PerformanceFilter;
import com.pd.finance.filter.db.OverviewFilter;
import com.pd.finance.filter.db.SwotFilter;
import com.pd.finance.filter.db.TechnicalPeriodFilter;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

public class EquitySearchRequest {

    private PerformanceFilter performanceFilter;
    private SwotFilter swotFilter;
    private OverviewFilter overviewFilter;
    private TechnicalPeriodFilter technicalPeriodFilter;
    private EquityInsightFilter insightFilter;

    public EquityInsightFilter getInsightFilter() {
        return insightFilter;
    }

    public void setInsightFilter(EquityInsightFilter insightFilter) {
        this.insightFilter = insightFilter;
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
