
package com.pd.finance.response.summary;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "maxAge",
    "currentPrice",
    "targetHighPrice",
    "targetLowPrice",
    "targetMeanPrice",
    "targetMedianPrice",
    "recommendationMean",
    "recommendationKey",
    "numberOfAnalystOpinions",
    "totalCash",
    "totalCashPerShare",
    "ebitda",
    "totalDebt",
    "totalRevenue",
    "debtToEquity",
    "revenuePerShare",
    "grossProfits",
    "revenueGrowth",
    "grossMargins",
    "ebitdaMargins",
    "operatingMargins",
    "profitMargins",
    "financialCurrency"
})
@Generated("jsonschema2pojo")
public class FinancialData {

    @JsonProperty("maxAge")
    private Long maxAge;
    @JsonProperty("currentPrice")
    private CurrentPrice currentPrice;
    @JsonProperty("targetHighPrice")
    private TargetHighPrice targetHighPrice;
    @JsonProperty("targetLowPrice")
    private TargetLowPrice targetLowPrice;
    @JsonProperty("targetMeanPrice")
    private TargetMeanPrice targetMeanPrice;
    @JsonProperty("targetMedianPrice")
    private TargetMedianPrice targetMedianPrice;
    @JsonProperty("recommendationMean")
    private RecommendationMean recommendationMean;
    @JsonProperty("recommendationKey")
    private String recommendationKey;
    @JsonProperty("numberOfAnalystOpinions")
    private NumberOfAnalystOpinions numberOfAnalystOpinions;
    @JsonProperty("totalCash")
    private TotalCash totalCash;
    @JsonProperty("totalCashPerShare")
    private TotalCashPerShare totalCashPerShare;
    @JsonProperty("ebitda")
    private Ebitda ebitda;
    @JsonProperty("totalDebt")
    private TotalDebt totalDebt;
    @JsonProperty("totalRevenue")
    private TotalRevenue totalRevenue;
    @JsonProperty("debtToEquity")
    private DebtToEquity debtToEquity;
    @JsonProperty("revenuePerShare")
    private RevenuePerShare revenuePerShare;
    @JsonProperty("grossProfits")
    private GrossProfits grossProfits;
    @JsonProperty("revenueGrowth")
    private RevenueGrowth revenueGrowth;
    @JsonProperty("grossMargins")
    private GrossMargins grossMargins;
    @JsonProperty("ebitdaMargins")
    private EbitdaMargins ebitdaMargins;
    @JsonProperty("operatingMargins")
    private OperatingMargins operatingMargins;
    @JsonProperty("profitMargins")
    private FinancialDataProfitMargins profitMargins;
    @JsonProperty("financialCurrency")
    private String financialCurrency;

    @JsonProperty("maxAge")
    public Long getMaxAge() {
        return maxAge;
    }

    @JsonProperty("maxAge")
    public void setMaxAge(Long maxAge) {
        this.maxAge = maxAge;
    }

    @JsonProperty("currentPrice")
    public CurrentPrice getCurrentPrice() {
        return currentPrice;
    }

    @JsonProperty("currentPrice")
    public void setCurrentPrice(CurrentPrice currentPrice) {
        this.currentPrice = currentPrice;
    }

    @JsonProperty("targetHighPrice")
    public TargetHighPrice getTargetHighPrice() {
        return targetHighPrice;
    }

    @JsonProperty("targetHighPrice")
    public void setTargetHighPrice(TargetHighPrice targetHighPrice) {
        this.targetHighPrice = targetHighPrice;
    }

    @JsonProperty("targetLowPrice")
    public TargetLowPrice getTargetLowPrice() {
        return targetLowPrice;
    }

    @JsonProperty("targetLowPrice")
    public void setTargetLowPrice(TargetLowPrice targetLowPrice) {
        this.targetLowPrice = targetLowPrice;
    }

    @JsonProperty("targetMeanPrice")
    public TargetMeanPrice getTargetMeanPrice() {
        return targetMeanPrice;
    }

    @JsonProperty("targetMeanPrice")
    public void setTargetMeanPrice(TargetMeanPrice targetMeanPrice) {
        this.targetMeanPrice = targetMeanPrice;
    }

    @JsonProperty("targetMedianPrice")
    public TargetMedianPrice getTargetMedianPrice() {
        return targetMedianPrice;
    }

    @JsonProperty("targetMedianPrice")
    public void setTargetMedianPrice(TargetMedianPrice targetMedianPrice) {
        this.targetMedianPrice = targetMedianPrice;
    }

    @JsonProperty("recommendationMean")
    public RecommendationMean getRecommendationMean() {
        return recommendationMean;
    }

    @JsonProperty("recommendationMean")
    public void setRecommendationMean(RecommendationMean recommendationMean) {
        this.recommendationMean = recommendationMean;
    }

    @JsonProperty("recommendationKey")
    public String getRecommendationKey() {
        return recommendationKey;
    }

    @JsonProperty("recommendationKey")
    public void setRecommendationKey(String recommendationKey) {
        this.recommendationKey = recommendationKey;
    }

    @JsonProperty("numberOfAnalystOpinions")
    public NumberOfAnalystOpinions getNumberOfAnalystOpinions() {
        return numberOfAnalystOpinions;
    }

    @JsonProperty("numberOfAnalystOpinions")
    public void setNumberOfAnalystOpinions(NumberOfAnalystOpinions numberOfAnalystOpinions) {
        this.numberOfAnalystOpinions = numberOfAnalystOpinions;
    }

    @JsonProperty("totalCash")
    public TotalCash getTotalCash() {
        return totalCash;
    }

    @JsonProperty("totalCash")
    public void setTotalCash(TotalCash totalCash) {
        this.totalCash = totalCash;
    }

    @JsonProperty("totalCashPerShare")
    public TotalCashPerShare getTotalCashPerShare() {
        return totalCashPerShare;
    }

    @JsonProperty("totalCashPerShare")
    public void setTotalCashPerShare(TotalCashPerShare totalCashPerShare) {
        this.totalCashPerShare = totalCashPerShare;
    }

    @JsonProperty("ebitda")
    public Ebitda getEbitda() {
        return ebitda;
    }

    @JsonProperty("ebitda")
    public void setEbitda(Ebitda ebitda) {
        this.ebitda = ebitda;
    }

    @JsonProperty("totalDebt")
    public TotalDebt getTotalDebt() {
        return totalDebt;
    }

    @JsonProperty("totalDebt")
    public void setTotalDebt(TotalDebt totalDebt) {
        this.totalDebt = totalDebt;
    }

    @JsonProperty("totalRevenue")
    public TotalRevenue getTotalRevenue() {
        return totalRevenue;
    }

    @JsonProperty("totalRevenue")
    public void setTotalRevenue(TotalRevenue totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    @JsonProperty("debtToEquity")
    public DebtToEquity getDebtToEquity() {
        return debtToEquity;
    }

    @JsonProperty("debtToEquity")
    public void setDebtToEquity(DebtToEquity debtToEquity) {
        this.debtToEquity = debtToEquity;
    }

    @JsonProperty("revenuePerShare")
    public RevenuePerShare getRevenuePerShare() {
        return revenuePerShare;
    }

    @JsonProperty("revenuePerShare")
    public void setRevenuePerShare(RevenuePerShare revenuePerShare) {
        this.revenuePerShare = revenuePerShare;
    }

    @JsonProperty("grossProfits")
    public GrossProfits getGrossProfits() {
        return grossProfits;
    }

    @JsonProperty("grossProfits")
    public void setGrossProfits(GrossProfits grossProfits) {
        this.grossProfits = grossProfits;
    }

    @JsonProperty("revenueGrowth")
    public RevenueGrowth getRevenueGrowth() {
        return revenueGrowth;
    }

    @JsonProperty("revenueGrowth")
    public void setRevenueGrowth(RevenueGrowth revenueGrowth) {
        this.revenueGrowth = revenueGrowth;
    }

    @JsonProperty("grossMargins")
    public GrossMargins getGrossMargins() {
        return grossMargins;
    }

    @JsonProperty("grossMargins")
    public void setGrossMargins(GrossMargins grossMargins) {
        this.grossMargins = grossMargins;
    }

    @JsonProperty("ebitdaMargins")
    public EbitdaMargins getEbitdaMargins() {
        return ebitdaMargins;
    }

    @JsonProperty("ebitdaMargins")
    public void setEbitdaMargins(EbitdaMargins ebitdaMargins) {
        this.ebitdaMargins = ebitdaMargins;
    }

    @JsonProperty("operatingMargins")
    public OperatingMargins getOperatingMargins() {
        return operatingMargins;
    }

    @JsonProperty("operatingMargins")
    public void setOperatingMargins(OperatingMargins operatingMargins) {
        this.operatingMargins = operatingMargins;
    }

    @JsonProperty("profitMargins")
    public FinancialDataProfitMargins getProfitMargins() {
        return profitMargins;
    }

    @JsonProperty("profitMargins")
    public void setProfitMargins(FinancialDataProfitMargins profitMargins) {
        this.profitMargins = profitMargins;
    }

    @JsonProperty("financialCurrency")
    public String getFinancialCurrency() {
        return financialCurrency;
    }

    @JsonProperty("financialCurrency")
    public void setFinancialCurrency(String financialCurrency) {
        this.financialCurrency = financialCurrency;
    }


}
