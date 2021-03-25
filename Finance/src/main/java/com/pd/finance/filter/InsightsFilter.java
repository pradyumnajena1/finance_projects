package com.pd.finance.filter;

import com.pd.finance.model.Equity;
import org.springframework.data.mongodb.core.query.Criteria;

public class InsightsFilter  implements  EquityFilter{

    private Integer minBullish;
    private Integer maxBearish;

    public Integer getMinBullish() {
        return minBullish;
    }

    public void setMinBullish(Integer minBullish) {
        this.minBullish = minBullish;
    }

    public Integer getMaxBearish() {
        return maxBearish;
    }

    public void setMaxBearish(Integer maxBearish) {
        this.maxBearish = maxBearish;
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
