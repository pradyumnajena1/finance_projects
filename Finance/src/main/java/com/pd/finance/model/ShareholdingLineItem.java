package com.pd.finance.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;
import java.util.Date;

public class ShareholdingLineItem {
    @JsonProperty("quarterDate")
    private Date quarterDate;

    @Field(targetType = FieldType.DECIMAL128)
    @JsonProperty("percentage")
    private BigDecimal percentage;

    public Date getQuarterDate() {
        return quarterDate;
    }

    public void setQuarterDate(Date quarterDate) {
        this.quarterDate = quarterDate;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }
}
