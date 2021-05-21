package com.pd.finance.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsiderTransaction {

    @JsonProperty("transactionDate")
    private Date transactionDate;

    @JsonProperty("transactionType")
    private String transactionType;

    @JsonProperty("insiderName")
    private String insiderName;

    @JsonProperty("insiderType")
    private String insiderType;

    @Field(targetType = FieldType.INT64)
    @JsonProperty("quantity")
    private BigInteger quantity;

    @Field(targetType = FieldType.DECIMAL128)
    @JsonProperty("price")
    private BigDecimal price;

    @Field(targetType = FieldType.DECIMAL128)
    @JsonProperty("tradedPercentage")
    private BigDecimal tradedPercentage;

    @Field(targetType = FieldType.DECIMAL128)
    @JsonProperty("postTransactionHoldPercentage")
    private BigDecimal postTransactionHoldPercentage;

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getInsiderName() {
        return insiderName;
    }

    public void setInsiderName(String insiderName) {
        this.insiderName = insiderName;
    }

    public String getInsiderType() {
        return insiderType;
    }

    public void setInsiderType(String insiderType) {
        this.insiderType = insiderType;
    }

    public BigInteger getQuantity() {
        return quantity;
    }

    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTradedPercentage() {
        return tradedPercentage;
    }

    public void setTradedPercentage(BigDecimal tradedPercentage) {
        this.tradedPercentage = tradedPercentage;
    }

    public BigDecimal getPostTransactionHoldPercentage() {
        return postTransactionHoldPercentage;
    }

    public void setPostTransactionHoldPercentage(BigDecimal postTransactionHoldPercentage) {
        this.postTransactionHoldPercentage = postTransactionHoldPercentage;
    }
}
