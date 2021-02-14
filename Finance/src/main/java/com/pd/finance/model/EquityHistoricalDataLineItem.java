package com.pd.finance.model;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class EquityHistoricalDataLineItem {

    @Nonnull
    private Date date;
    @Nonnull
    private BigDecimal open;
    @Nonnull
    private BigDecimal close;
    @Nonnull
    private BigDecimal low;
    @Nonnull
    private BigDecimal high;

    private BigDecimal adjustedClose;
    @Nonnull
    private BigInteger volume;

    public EquityHistoricalDataLineItem(@Nonnull Date date, @Nonnull BigDecimal open, @Nonnull BigDecimal close, @Nonnull BigDecimal low, @Nonnull BigDecimal high, BigDecimal adjustedClose, @Nonnull BigInteger volume) {
        this.date = date;
        this.open = open;
        this.close = close;
        this.low = low;
        this.high = high;
        this.adjustedClose = adjustedClose;
        this.volume = volume;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    public BigDecimal getClose() {
        return close;
    }

    public void setClose(BigDecimal close) {
        this.close = close;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getAdjustedClose() {
        return adjustedClose;
    }

    public void setAdjustedClose(BigDecimal adjustedClose) {
        this.adjustedClose = adjustedClose;
    }

    public BigInteger getVolume() {
        return volume;
    }

    public void setVolume(BigInteger volume) {
        this.volume = volume;
    }
}
