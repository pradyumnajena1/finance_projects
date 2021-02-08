package com.pd.finance.request;

import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

public class TechnicalPeriodFilter {
    private TechnicalFilter dailyTechnicalFilter;
    private TechnicalFilter weeklyTechnicalFilter;
    private TechnicalFilter monthlyTechnicalFilter;

    public TechnicalFilter getDailyTechnicalFilter() {
        return dailyTechnicalFilter;
    }

    public void setDailyTechnicalFilter(TechnicalFilter dailyTechnicalFilter) {
        this.dailyTechnicalFilter = dailyTechnicalFilter;
    }

    public TechnicalFilter getWeeklyTechnicalFilter() {
        return weeklyTechnicalFilter;
    }

    public void setWeeklyTechnicalFilter(TechnicalFilter weeklyTechnicalFilter) {
        this.weeklyTechnicalFilter = weeklyTechnicalFilter;
    }

    public TechnicalFilter getMonthlyTechnicalFilter() {
        return monthlyTechnicalFilter;
    }

    public void setMonthlyTechnicalFilter(TechnicalFilter monthlyTechnicalFilter) {
        this.monthlyTechnicalFilter = monthlyTechnicalFilter;
    }
    public List<FieldError> validate(){
        List<FieldError> errors = new ArrayList<>();
        if(dailyTechnicalFilter!=null){
            errors.addAll(dailyTechnicalFilter.validate());
        }
        if(weeklyTechnicalFilter!=null){
            errors.addAll(weeklyTechnicalFilter.validate());
        }
        if(monthlyTechnicalFilter!=null){
            errors.addAll(monthlyTechnicalFilter.validate());
        }
        return errors;
    }
}
