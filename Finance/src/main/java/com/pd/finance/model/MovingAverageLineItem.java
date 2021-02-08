package com.pd.finance.model;

import java.math.BigDecimal;

public class MovingAverageLineItem {

    private int period;
    private BigDecimal SMA;
    private String indication;

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public BigDecimal getSMA() {
        return SMA;
    }

    public void setSMA(BigDecimal SMA) {
        this.SMA = SMA;
    }

    public String getIndication() {
        return indication;
    }

    public void setIndication(String indication) {
        this.indication = indication;
    }
}
