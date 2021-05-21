package com.pd.finance.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FinancialInsights {

    @Field(targetType =  FieldType.DECIMAL128)
    @JsonProperty("piotroskiScore")
    private BigDecimal piotroskiScore;

    @Field(targetType =  FieldType.DECIMAL128)
    @JsonProperty("revenueCagrGrowth")
    private BigDecimal revenueCagrGrowth;

    @Field(targetType =  FieldType.DECIMAL128)
    @JsonProperty("netProfitCagrGrowth")
    private BigDecimal netProfitCagrGrowth;

    @Field(targetType =  FieldType.DECIMAL128)
    @JsonProperty("operatingProfitCagrGrowth")
    private BigDecimal operatingProfitCagrGrowth;

    public BigDecimal getPiotroskiScore() {
        return piotroskiScore;
    }

    public void setPiotroskiScore(BigDecimal piotroskiScore) {
        this.piotroskiScore = piotroskiScore;
    }

    public BigDecimal getRevenueCagrGrowth() {
        return revenueCagrGrowth;
    }

    public void setRevenueCagrGrowth(BigDecimal revenueCagrGrowth) {
        this.revenueCagrGrowth = revenueCagrGrowth;
    }

    public BigDecimal getNetProfitCagrGrowth() {
        return netProfitCagrGrowth;
    }

    public void setNetProfitCagrGrowth(BigDecimal netProfitCagrGrowth) {
        this.netProfitCagrGrowth = netProfitCagrGrowth;
    }

    public BigDecimal getOperatingProfitCagrGrowth() {
        return operatingProfitCagrGrowth;
    }

    public void setOperatingProfitCagrGrowth(BigDecimal operatingProfitCagrGrowth) {
        this.operatingProfitCagrGrowth = operatingProfitCagrGrowth;
    }
}
