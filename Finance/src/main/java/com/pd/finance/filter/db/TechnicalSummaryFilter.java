package com.pd.finance.filter.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pd.finance.filter.FilterType;
import com.pd.finance.filter.IFilter;
import com.pd.finance.model.Equity;
import com.pd.finance.model.TechAnalysisSummaryValue;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.List;

public class TechnicalSummaryFilter  implements IFilter<TechAnalysisSummaryValue> {
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
    @JsonIgnore
    public FilterType getFilterType() {
        return FilterType.InDb;
    }

    @Override
    public Criteria getCriteria(String parentObject) {
        List<Criteria> criteriaList = new ArrayList<>();

        criteriaList.add(Criteria.where(parentObject+".bullish").exists(true).andOperator(Criteria.where(parentObject+".bullish").gte(minBullish)));
        criteriaList.add(Criteria.where(parentObject+".bearish").exists(true).andOperator(Criteria.where(parentObject+".bearish").lte(maxBearish)));

        Criteria criteria = new Criteria().andOperator(criteriaList.toArray( new Criteria[criteriaList.size()]));
        return criteria;
    }

    @Override
    public boolean apply(TechAnalysisSummaryValue obj) {
        return false;
    }
}
