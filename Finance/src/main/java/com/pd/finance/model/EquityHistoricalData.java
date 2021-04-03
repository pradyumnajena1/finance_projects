package com.pd.finance.model;

import com.pd.finance.service.HistoricalDataInterval;

public class EquityHistoricalData extends EquityAttribute{

    private EquityHistoricalIntervalData dailyHistoricalData;
    private EquityHistoricalIntervalData weeklyHistoricalIntervalData;
    private EquityHistoricalIntervalData monthlyEquityHistoricalIntervalData;


    public EquityHistoricalIntervalData getDailyHistoricalData() {
        return dailyHistoricalData;
    }

    public void setDailyHistoricalData(EquityHistoricalIntervalData dailyHistoricalData) {
        this.dailyHistoricalData = dailyHistoricalData;
    }

    public EquityHistoricalIntervalData getWeeklyHistoricalIntervalData() {
        return weeklyHistoricalIntervalData;
    }

    public void setWeeklyHistoricalIntervalData(EquityHistoricalIntervalData weeklyHistoricalIntervalData) {
        this.weeklyHistoricalIntervalData = weeklyHistoricalIntervalData;
    }

    public EquityHistoricalIntervalData getMonthlyEquityHistoricalIntervalData() {
        return monthlyEquityHistoricalIntervalData;
    }

    public void setMonthlyEquityHistoricalIntervalData(EquityHistoricalIntervalData monthlyEquityHistoricalIntervalData) {
        this.monthlyEquityHistoricalIntervalData = monthlyEquityHistoricalIntervalData;
    }

    public EquityHistoricalIntervalData getIntervalData(HistoricalDataInterval interval){
        if(interval.equals(HistoricalDataInterval.OneDay)){
            return dailyHistoricalData;
        }else if(interval.equals(HistoricalDataInterval.OneWeek)){
            return weeklyHistoricalIntervalData;
        }else if (interval.equals(HistoricalDataInterval.OneMonth)){
            return monthlyEquityHistoricalIntervalData;
        }else {
            throw new IllegalArgumentException(interval.getIntervalString() + "not yet supported");
        }
    }
}
