package com.pd.finance.request;

import java.math.BigDecimal;
import java.math.BigInteger;

public class OverviewFilter {
    private BigDecimal maxPE = BigDecimal.valueOf(100000);
    private BigInteger minVolume = BigInteger.ZERO;

    public BigDecimal getMaxPE() {
        return maxPE;
    }

    public void setMaxPE(BigDecimal maxPE) {
        this.maxPE = maxPE;
    }

    public BigInteger getMinVolume() {
        return minVolume;
    }

    public void setMinVolume(BigInteger minVolume) {
        this.minVolume = minVolume;
    }
}
