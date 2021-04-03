package com.pd.finance.model;

import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;

public class CompoundedSalesGrowthDetails {
    @Field(targetType = FieldType.DECIMAL128)
    BigDecimal lastTenYears;

    @Field(targetType = FieldType.DECIMAL128)
    BigDecimal lastFiveYears;

    @Field(targetType = FieldType.DECIMAL128)
    BigDecimal lastThreeYears;

    @Field(targetType = FieldType.DECIMAL128)
    BigDecimal ttm;

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

    public BigDecimal getTtm() {
        return ttm;
    }

    public void setTtm(BigDecimal ttm) {
        this.ttm = ttm;
    }
}
