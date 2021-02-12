package com.pd.finance.model;

import java.math.BigDecimal;

public class EquityCurrentPriceStats {
    BigDecimal high;
    BigDecimal low;
    BigDecimal lastPrice;
    BigDecimal prevClose;
    BigDecimal change;
    BigDecimal percentageGain;

    public EquityCurrentPriceStats() {
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(BigDecimal lastPrice) {
        this.lastPrice = lastPrice;
    }

    public BigDecimal getPrevClose() {
        return prevClose;
    }

    public void setPrevClose(BigDecimal prevClose) {
        this.prevClose = prevClose;
    }

    public BigDecimal getChange() {
        return change;
    }

    public void setChange(BigDecimal change) {
        this.change = change;
    }

    public BigDecimal getPercentageGain() {
        return percentageGain;
    }

    public void setPercentageGain(BigDecimal percentageGain) {
        this.percentageGain = percentageGain;
    }
}