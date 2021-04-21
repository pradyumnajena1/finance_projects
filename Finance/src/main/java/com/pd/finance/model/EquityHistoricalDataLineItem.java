package com.pd.finance.model;

import com.pd.finance.utils.CommonUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class EquityHistoricalDataLineItem  implements Comparable<EquityHistoricalDataLineItem>{

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    @Nonnull
    private Date date;

    @Nonnull
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal open;


    @Nonnull
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal close;


    @Nonnull
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal low;


    @Nonnull
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal high;

    @Nonnull
    @Field(targetType = FieldType.DECIMAL128)
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
    public boolean isAllFieldsAvailable() {
        return  getClose()!= null &&  getOpen() != null && getHigh() != null && getLow() != null;
    }

    public boolean isWithinRange(EquityHistoricalDataLineItem pivotLineItem, BigDecimal percentage) {

        BigDecimal lowerBound = CommonUtils.getLowerBound(pivotLineItem.getClose(), percentage);
        BigDecimal upperBound = CommonUtils.getUpperBound(pivotLineItem.getClose(), percentage);

        return isWithinRange(lowerBound,upperBound);
    }

    public boolean isWithinRange(BigDecimal lowerBound,BigDecimal upperBound ) {

        if(!CommonUtils.isBetween( getClose(), lowerBound,upperBound)){
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HDLineItem{");
        sb.append("date=").append(DATE_FORMAT.format( date));
        sb.append(", close=").append(close);
        sb.append('}');
        return sb.toString();
    }
}
