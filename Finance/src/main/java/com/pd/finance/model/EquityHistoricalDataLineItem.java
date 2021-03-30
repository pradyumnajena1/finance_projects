package com.pd.finance.model;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Objects;

public class EquityHistoricalDataLineItem  implements Comparable<EquityHistoricalDataLineItem>{

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EquityHistoricalDataLineItem lineItem = (EquityHistoricalDataLineItem) o;
        return date.equals(lineItem.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }

    @Override
    public int compareTo(@NotNull EquityHistoricalDataLineItem other) {
       if(other==null) return 1;

        final boolean f1, f2;
        return (f1 = this.date == null) ^ (f2 = other.date == null) ? (f1 ? -1 : 1 ): (f1 && f2 ? 0 : this.date.compareTo(other.date) );
    }
}
