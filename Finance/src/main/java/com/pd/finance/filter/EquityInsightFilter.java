package com.pd.finance.filter;

import com.pd.finance.model.Equity;
import org.springframework.data.mongodb.core.query.Criteria;

public class EquityInsightFilter  implements  EquityFilter{

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

    @Override
    public FilterType getFilterType() {
        return null;
    }

    @Override
    public Criteria getCriteria(String pa) {
        return null;
    }

    @Override
    public boolean apply(Equity obj) {
        return false;
    }
}
