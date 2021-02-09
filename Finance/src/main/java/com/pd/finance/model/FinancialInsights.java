package com.pd.finance.model;

import java.math.BigDecimal;

public class FinancialInsights {

    private BigDecimal piotroskiScore;
    private BigDecimal revenueCagrGrowth;
    private BigDecimal netProfitCagrGrowth;
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
