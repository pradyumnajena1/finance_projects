package com.pd.finance.model;

public class TechnicalDetails {
    private TechnicalDetails daily;
    private TechnicalDetails weekly;
    private TechnicalDetails monthly;

    public TechnicalDetails() {
    }

    public TechnicalDetails getDaily() {
        return daily;
    }

    public void setDaily(TechnicalDetails daily) {
        this.daily = daily;
    }

    public TechnicalDetails getWeekly() {
        return weekly;
    }

    public void setWeekly(TechnicalDetails weekly) {
        this.weekly = weekly;
    }

    public TechnicalDetails getMonthly() {
        return monthly;
    }

    public void setMonthly(TechnicalDetails monthly) {
        this.monthly = monthly;
    }
}
