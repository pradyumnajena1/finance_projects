package com.pd.finance.model.equity.summary;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pd.finance.response.summary.*;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

public class DefaultKeyStatistics {

    @JsonProperty("maxAge")
    @Field(targetType = FieldType.INT64)
    private Long maxAge;

    @JsonProperty("priceHint")
    @Field(targetType = FieldType.INT64)
    private Long priceHint;

    @JsonProperty("enterpriseValue")
    @Field(targetType = FieldType.INT64)
    private Long enterpriseValue;

    @JsonProperty("forwardPE")
    @Field(targetType = FieldType.DOUBLE)
    private Double forwardPE;

    @JsonProperty("profitMargins")
    @Field(targetType = FieldType.DOUBLE)
    private Double profitMargins;

    @JsonProperty("floatShares")
    @Field(targetType = FieldType.INT64)
    private Long floatShares;

    @JsonProperty("sharesOutstanding")
    @Field(targetType = FieldType.INT64)
    private Long sharesOutstanding;

    @JsonProperty("heldPercentInsiders")
    @Field(targetType = FieldType.DOUBLE)
    private Double heldPercentInsiders;

    @JsonProperty("heldPercentInstitutions")
    @Field(targetType = FieldType.DOUBLE)
    private Double heldPercentInstitutions;

    @JsonProperty("bookValue")
    @Field(targetType = FieldType.DOUBLE)
    private Double bookValue;

    @JsonProperty("priceToBook")
    @Field(targetType = FieldType.DOUBLE)
    private Double priceToBook;

    @JsonProperty("trailingEps")
    @Field(targetType = FieldType.DOUBLE)
    private Double trailingEps;

    @JsonProperty("forwardEps")
    @Field(targetType = FieldType.DOUBLE)
    private Double forwardEps;

    @JsonProperty("enterpriseToRevenue")
    @Field(targetType = FieldType.DOUBLE)
    private Double enterpriseToRevenue;

    @JsonProperty("enterpriseToEbitda")
    @Field(targetType = FieldType.DOUBLE)
    private Double enterpriseToEbitda;

    @JsonProperty("52WeekChange")
    @Field(targetType = FieldType.DOUBLE)
    private Double FiftyTwoWeekChange;

    @JsonProperty("SandP52WeekChange")
    @Field(targetType = FieldType.DOUBLE)
    private Double sandP52WeekChange;

    public Long getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Long maxAge) {
        this.maxAge = maxAge;
    }

    public Long getPriceHint() {
        return priceHint;
    }

    public void setPriceHint(Long priceHint) {
        this.priceHint = priceHint;
    }

    public Long getEnterpriseValue() {
        return enterpriseValue;
    }

    public void setEnterpriseValue(Long enterpriseValue) {
        this.enterpriseValue = enterpriseValue;
    }

    public Double getForwardPE() {
        return forwardPE;
    }

    public void setForwardPE(Double forwardPE) {
        this.forwardPE = forwardPE;
    }

    public Double getProfitMargins() {
        return profitMargins;
    }

    public void setProfitMargins(Double profitMargins) {
        this.profitMargins = profitMargins;
    }

    public Long getFloatShares() {
        return floatShares;
    }

    public void setFloatShares(Long floatShares) {
        this.floatShares = floatShares;
    }

    public Long getSharesOutstanding() {
        return sharesOutstanding;
    }

    public void setSharesOutstanding(Long sharesOutstanding) {
        this.sharesOutstanding = sharesOutstanding;
    }

    public Double getHeldPercentInsiders() {
        return heldPercentInsiders;
    }

    public void setHeldPercentInsiders(Double heldPercentInsiders) {
        this.heldPercentInsiders = heldPercentInsiders;
    }

    public Double getHeldPercentInstitutions() {
        return heldPercentInstitutions;
    }

    public void setHeldPercentInstitutions(Double heldPercentInstitutions) {
        this.heldPercentInstitutions = heldPercentInstitutions;
    }

    public Double getBookValue() {
        return bookValue;
    }

    public void setBookValue(Double bookValue) {
        this.bookValue = bookValue;
    }

    public Double getPriceToBook() {
        return priceToBook;
    }

    public void setPriceToBook(Double priceToBook) {
        this.priceToBook = priceToBook;
    }

    public Double getTrailingEps() {
        return trailingEps;
    }

    public void setTrailingEps(Double trailingEps) {
        this.trailingEps = trailingEps;
    }

    public Double getForwardEps() {
        return forwardEps;
    }

    public void setForwardEps(Double forwardEps) {
        this.forwardEps = forwardEps;
    }

    public Double getEnterpriseToRevenue() {
        return enterpriseToRevenue;
    }

    public void setEnterpriseToRevenue(Double enterpriseToRevenue) {
        this.enterpriseToRevenue = enterpriseToRevenue;
    }

    public Double getEnterpriseToEbitda() {
        return enterpriseToEbitda;
    }

    public void setEnterpriseToEbitda(Double enterpriseToEbitda) {
        this.enterpriseToEbitda = enterpriseToEbitda;
    }

    public Double getFiftyTwoWeekChange() {
        return FiftyTwoWeekChange;
    }

    public void setFiftyTwoWeekChange(Double fiftyTwoWeekChange) {
        FiftyTwoWeekChange = fiftyTwoWeekChange;
    }

    public Double getSandP52WeekChange() {
        return sandP52WeekChange;
    }

    public void setSandP52WeekChange(Double sandP52WeekChange) {
        this.sandP52WeekChange = sandP52WeekChange;
    }
}
