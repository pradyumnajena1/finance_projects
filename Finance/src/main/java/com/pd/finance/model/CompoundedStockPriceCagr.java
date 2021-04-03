package com.pd.finance.model;

import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;

public class CompoundedStockPriceCagr {
    @Field(targetType = FieldType.DECIMAL128)
    BigDecimal lastTenYears;

    @Field(targetType = FieldType.DECIMAL128)
    BigDecimal lastFiveYears;

    @Field(targetType = FieldType.DECIMAL128)
    BigDecimal lastThreeYears;

    @Field(targetType = FieldType.DECIMAL128)
    BigDecimal lastOneYear;

    public BigDecimal getLastTenYears() {
        return lastTenYears;
    }

    public void setLastTenYears(BigDecimal lastTenYears) {
        this.lastTenYears = lastTenYears;
    }

    public BigDecimal getLastFiveYears() {
        return lastFiveYears;
    }

    public void setLastFiveYears(BigDecimal lastFiveYears) {
        this.lastFiveYears = lastFiveYears;
    }

    public BigDecimal getLastThreeYears() {
        return lastThreeYears;
    }

    public void setLastThreeYears(BigDecimal lastThreeYears) {
        this.lastThreeYears = lastThreeYears;
    }

    public BigDecimal getLastOneYear() {
        return lastOneYear;
    }

    public void setLastOneYear(BigDecimal lastOneYear) {
        this.lastOneYear = lastOneYear;
    }
}
