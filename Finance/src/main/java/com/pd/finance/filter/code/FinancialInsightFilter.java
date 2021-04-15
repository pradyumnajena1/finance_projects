package com.pd.finance.filter.code;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pd.finance.filter.EquityFilter;
import com.pd.finance.filter.FilterType;
import com.pd.finance.filter.IFilter;
import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityInsights;
import com.pd.finance.model.FinancialInsights;
import org.springframework.data.mongodb.core.query.Criteria;

import java.math.BigDecimal;

public class FinancialInsightFilter  implements IFilter<FinancialInsights> {

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
    @JsonIgnore
    public FilterType getFilterType() {
        return FilterType.InCode;
    }

    @Override
    public Criteria getCriteria(String parentObject) {
        throw new UnsupportedOperationException()  ;
    }

    @Override
    public boolean apply(FinancialInsights financialInsights) {
        if(financialInsights==null){
            return false;
        }
        BigDecimal minNetProfitCagrGrowth =  getMinNetProfitCagrGrowth();
        BigDecimal netProfitCagrGrowth = financialInsights.getNetProfitCagrGrowth();
        if (!isValid(minNetProfitCagrGrowth, netProfitCagrGrowth)) return false;

        BigDecimal minOperatingProfitCagrGrowth =  getMinOperatingProfitCagrGrowth();
        BigDecimal operatingProfitCagrGrowth = financialInsights.getOperatingProfitCagrGrowth();
        if (!isValid(minOperatingProfitCagrGrowth, operatingProfitCagrGrowth)) return false;

        BigDecimal minRevenueCagrGrowth =  getMinRevenueCagrGrowth();
        BigDecimal revenueCagrGrowth = financialInsights.getRevenueCagrGrowth();
        if (!isValid(minRevenueCagrGrowth, revenueCagrGrowth)) return false;

        BigDecimal minPiotroskiScore =  getMinPiotroskiScore();
        BigDecimal piotroskiScore = financialInsights.getPiotroskiScore();
        if (!isValid(minPiotroskiScore, piotroskiScore)) return false;



        return true;
    }


    private boolean isValid(BigDecimal minExpectedValue, BigDecimal actualValue) {
        if (minExpectedValue != null) {

            if (actualValue == null) {
                return false;
            }
            if (minExpectedValue.compareTo(actualValue) > 0) {
                return false;
            }
        }
        return true;
    }
}
