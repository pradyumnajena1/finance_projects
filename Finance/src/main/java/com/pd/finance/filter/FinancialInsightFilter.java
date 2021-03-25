package com.pd.finance.filter;

import com.pd.finance.model.Equity;
import org.springframework.data.mongodb.core.query.Criteria;

import java.math.BigDecimal;

public class FinancialInsightFilter  implements  EquityFilter{
    private BigDecimal minPiotroskiScore;
    private BigDecimal minRevenueCagrGrowth;
    private BigDecimal minNetProfitCagrGrowth;
    private BigDecimal minOperatingProfitCagrGrowth;

    public BigDecimal getMinPiotroskiScore() {
        return minPiotroskiScore;
    }

    public void setMinPiotroskiScore(BigDecimal minPiotroskiScore) {
        this.minPiotroskiScore = minPiotroskiScore;
    }

    public BigDecimal getMinRevenueCagrGrowth() {
        return minRevenueCagrGrowth;
    }

    public void setMinRevenueCagrGrowth(BigDecimal minRevenueCagrGrowth) {
        this.minRevenueCagrGrowth = minRevenueCagrGrowth;
    }

    public BigDecimal getMinNetProfitCagrGrowth() {
        return minNetProfitCagrGrowth;
    }

    public void setMinNetProfitCagrGrowth(BigDecimal minNetProfitCagrGrowth) {
        this.minNetProfitCagrGrowth = minNetProfitCagrGrowth;
    }

    public BigDecimal getMinOperatingProfitCagrGrowth() {
        return minOperatingProfitCagrGrowth;
    }

    public void setMinOperatingProfitCagrGrowth(BigDecimal minOperatingProfitCagrGrowth) {
        this.minOperatingProfitCagrGrowth = minOperatingProfitCagrGrowth;
    }

    @Override
    public FilterType getFilterType() {
        return null;
    }

    @Override
    public Criteria getCriteria(String parentObject) {
        return null;
    }

    @Override
    public boolean apply(Equity obj) {
        return false;
    }
}
