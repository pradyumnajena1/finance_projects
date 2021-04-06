package com.pd.finance.model;

import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;
import java.util.Date;

public class EquityPerformance {

    private Date date;
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal price;

    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal change;

    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal changePercent;



    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getChange() {
        return change;
    }

    public void setChange(BigDecimal change) {
        this.change = change;
    }

    public BigDecimal getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(BigDecimal changePercent) {
        this.changePercent = changePercent;
    }
}
