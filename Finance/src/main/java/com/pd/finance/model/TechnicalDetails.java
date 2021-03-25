package com.pd.finance.model;

public class TechnicalDetails extends EquityAttribute{


    private TechnicalAnalysis daily;
    private TechnicalAnalysis weekly;
    private TechnicalAnalysis monthly;

    public TechnicalDetails() {
    }

    public TechnicalAnalysis getDaily() {
        return daily;
    }

    public void setDaily(TechnicalAnalysis daily) {
        this.daily = daily;
    }

    public TechnicalAnalysis getWeekly() {
        return weekly;
    }

    public void setWeekly(TechnicalAnalysis weekly) {
        this.weekly = weekly;
    }

    public TechnicalAnalysis getMonthly() {
        return monthly;
    }

    public void setMonthly(TechnicalAnalysis monthly) {
        this.monthly = monthly;
    }
}
