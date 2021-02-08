package com.pd.finance.model;

public class MovingAvgCrossoverLineItem {
    private String period;
    private String movingAvgCrossover;
    private String indication;

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getMovingAvgCrossover() {
        return movingAvgCrossover;
    }

    public void setMovingAvgCrossover(String movingAvgCrossover) {
        this.movingAvgCrossover = movingAvgCrossover;
    }

    public String getIndication() {
        return indication;
    }

    public void setIndication(String indication) {
        this.indication = indication;
    }
}
