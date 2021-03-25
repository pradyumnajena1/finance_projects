package com.pd.finance.model;

import java.math.BigDecimal;
import java.math.BigInteger;

public class EquityOverview extends EquityAttribute{

    private BigInteger volume;
    private BigInteger marketCap;
    private BigDecimal faceValue;
    private BigDecimal bookValue;
    private BigDecimal stockPE;
    private  BigDecimal stockPB;

    public EquityOverview() {
    }

    public BigInteger getVolume() {
        return volume;
    }

    public void setVolume(BigInteger volume) {
        this.volume = volume;
    }

    public BigInteger getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(BigInteger marketCap) {
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
