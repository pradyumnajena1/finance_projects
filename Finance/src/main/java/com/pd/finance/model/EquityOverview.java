package com.pd.finance.model;

import java.math.BigDecimal;
import java.math.BigInteger;

public class EquityOverview {

    private BigInteger volume;
    private BigInteger marketCap;
    private BigInteger faceValue;
    private BigInteger bookValue;
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

    public BigInteger getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(BigInteger faceValue) {
        this.faceValue = faceValue;
    }

    public BigInteger getBookValue() {
        return bookValue;
    }

    public void setBookValue(BigInteger bookValue) {
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
