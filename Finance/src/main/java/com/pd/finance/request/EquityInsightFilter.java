package com.pd.finance.request;

import com.pd.finance.model.Equity;

public class EquityInsightFilter {

    private InsightsFilter shareholdingPatternsFilter;
    private InsightsFilter industryComparisionFilter;
    private InsightsFilter priceInsightFilter;
    private FinancialInsightFilter financialInsightFilter;




    public InsightsFilter getShareholdingPatternsFilter() {
        return shareholdingPatternsFilter;
    }

    public void setShareholdingPatternsFilter(InsightsFilter shareholdingPatternsFilter) {
        this.shareholdingPatternsFilter = shareholdingPatternsFilter;
    }

    public InsightsFilter getIndustryComparisionFilter() {
        return industryComparisionFilter;
    }

    public void setIndustryComparisionFilter(InsightsFilter industryComparisionFilter) {
        this.industryComparisionFilter = industryComparisionFilter;
    }

    public InsightsFilter getPriceInsightFilter() {
        return priceInsightFilter;
    }

    public void setPriceInsightFilter(InsightsFilter priceInsightFilter) {
        this.priceInsightFilter = priceInsightFilter;
    }

    public FinancialInsightFilter getFinancialInsightFilter() {
        return financialInsightFilter;
    }

    public void setFinancialInsightFilter(FinancialInsightFilter financialInsightFilter) {
        this.financialInsightFilter = financialInsightFilter;
    }
}
