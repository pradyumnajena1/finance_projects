package com.pd.finance.model;

import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;
import java.math.BigInteger;

public class EquityOverview extends EquityAttribute{

    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal volume;
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal marketCap;
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal faceValue;
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal bookValue;
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal stockPE;
    @Field(targetType = FieldType.DECIMAL128)
    private  BigDecimal stockPB;
    @Field(targetType = FieldType.STRING)
    private String sector;

    public EquityOverview() {
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public BigDecimal getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(BigDecimal marketCap) {
        this.marketCap = marketCap;
    }

    public BigDecimal getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(BigDecimal faceValue) {
        this.faceValue = faceValue;
    }

    public BigDecimal getBookValue() {
        return bookValue;
    }

    public void setBookValue(BigDecimal bookValue) {
        this.bookValue = bookValue;
    }

    public BigDecimal getStockPE() {
        return stockPE;
    }

    public void setStockPE(BigDecimal stockPE) {
        this.stockPE = stockPE;
    }

    public BigDecimal getStockPB() {
        return stockPB;
    }

    public void setStockPB(BigDecimal stockPB) {
        this.stockPB = stockPB;
    }
}
