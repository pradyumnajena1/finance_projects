package com.pd.finance.model.equity.summary;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;


public class FinancialData {

    @Field(targetType = FieldType.INT64)
    private Long maxAge;

    @Field(targetType = FieldType.DOUBLE)
    private Double currentPrice;

    @Field(targetType = FieldType.INT64)
    private Long targetHighPrice;

    @Field(targetType = FieldType.INT64)
    private Long targetLowPrice;

    @Field(targetType = FieldType.INT64)
    private Long targetMeanPrice;

    @Field(targetType = FieldType.INT64)
    private Long targetMedianPrice;

    @Field(targetType = FieldType.DOUBLE)
    private Double recommendationMean;

    private String recommendationKey;

    @Field(targetType = FieldType.INT64)
    private Long numberOfAnalystOpinions;

    @Field(targetType = FieldType.INT64)
    private Long totalCash;

    @Field(targetType = FieldType.DOUBLE)
    private Double totalCashPerShare;

    @Field(targetType = FieldType.INT64)
    private Long ebitda;

    @Field(targetType = FieldType.INT64)
    private Long totalDebt;

    @Field(targetType = FieldType.INT64)
    private Long totalRevenue;

    @Field(targetType = FieldType.DOUBLE)
    private Double debtToEquity;

    @Field(targetType = FieldType.DOUBLE)
    private Double revenuePerShare;

    @Field(targetType = FieldType.INT64)
    private Long grossProfits;

    @Field(targetType = FieldType.DOUBLE)
    private Double revenueGrowth;

    @Field(targetType = FieldType.DOUBLE)
    private Double grossMargins;

    @Field(targetType = FieldType.DOUBLE)
    private Double ebitdaMargins;

    @Field(targetType = FieldType.DOUBLE)
    private Double operatingMargins;

    @Field(targetType = FieldType.DOUBLE)
    private Double profitMargins;

    private String financialCurrency;

    public Long getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Long maxAge) {
        this.maxAge = maxAge;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Long getTargetHighPrice() {
        return targetHighPrice;
    }

    public void setTargetHighPrice(Long targetHighPrice) {
        this.targetHighPrice = targetHighPrice;
    }

    public Long getTargetLowPrice() {
        return targetLowPrice;
    }

    public void setTargetLowPrice(Long targetLowPrice) {
        this.targetLowPrice = targetLowPrice;
    }

    public Long getTargetMeanPrice() {
        return targetMeanPrice;
    }

    public void setTargetMeanPrice(Long targetMeanPrice) {
        this.targetMeanPrice = targetMeanPrice;
    }

    public Long getTargetMedianPrice() {
        return targetMedianPrice;
    }

    public void setTargetMedianPrice(Long targetMedianPrice) {
        this.targetMedianPrice = targetMedianPrice;
    }

    public Double getRecommendationMean() {
        return recommendationMean;
    }

    public void setRecommendationMean(Double recommendationMean) {
        this.recommendationMean = recommendationMean;
    }

    public String getRecommendationKey() {
        return recommendationKey;
    }

    public void setRecommendationKey(String recommendationKey) {
        this.recommendationKey = recommendationKey;
    }

    public Long getNumberOfAnalystOpinions() {
        return numberOfAnalystOpinions;
    }

    public void setNumberOfAnalystOpinions(Long numberOfAnalystOpinions) {
        this.numberOfAnalystOpinions = numberOfAnalystOpinions;
    }

    public Long getTotalCash() {
        return totalCash;
    }

    public void setTotalCash(Long totalCash) {
        this.totalCash = totalCash;
    }

    public Double getTotalCashPerShare() {
        return totalCashPerShare;
    }

    public void setTotalCashPerShare(Double totalCashPerShare) {
        this.totalCashPerShare = totalCashPerShare;
    }

    public Long getEbitda() {
        return ebitda;
    }

    public void setEbitda(Long ebitda) {
        this.ebitda = ebitda;
    }

    public Long getTotalDebt() {
        return totalDebt;
    }

    public void setTotalDebt(Long totalDebt) {
        this.totalDebt = totalDebt;
    }

    public Long getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Long totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Double getDebtToEquity() {
        return debtToEquity;
    }

    public void setDebtToEquity(Double debtToEquity) {
        this.debtToEquity = debtToEquity;
    }

    public Double getRevenuePerShare() {
        return revenuePerShare;
    }

    public void setRevenuePerShare(Double revenuePerShare) {
        this.revenuePerShare = revenuePerShare;
    }

    public Long getGrossProfits() {
        return grossProfits;
    }

    public void setGrossProfits(Long grossProfits) {
        this.grossProfits = grossProfits;
    }

    public Double getRevenueGrowth() {
        return revenueGrowth;
    }

    public void setRevenueGrowth(Double revenueGrowth) {
        this.revenueGrowth = revenueGrowth;
    }

    public Double getGrossMargins() {
        return grossMargins;
    }

    public void setGrossMargins(Double grossMargins) {
        this.grossMargins = grossMargins;
    }

    public Double getEbitdaMargins() {
        return ebitdaMargins;
    }

    public void setEbitdaMargins(Double ebitdaMargins) {
        this.ebitdaMargins = ebitdaMargins;
    }

    public Double getOperatingMargins() {
        return operatingMargins;
    }

    public void setOperatingMargins(Double operatingMargins) {
        this.operatingMargins = operatingMargins;
    }

    public Double getProfitMargins() {
        return profitMargins;
    }

    public void setProfitMargins(Double profitMargins) {
        this.profitMargins = profitMargins;
    }

    public String getFinancialCurrency() {
        return financialCurrency;
    }

    public void setFinancialCurrency(String financialCurrency) {
        this.financialCurrency = financialCurrency;
    }
}
