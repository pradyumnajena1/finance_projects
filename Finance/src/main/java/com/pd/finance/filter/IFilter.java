package com.pd.finance.filter;

import org.springframework.data.mongodb.core.query.Criteria;

public interface IFilter<T> {

    public FilterType getFilterType();
    public Criteria getCriteria(String parentObject);
    public boolean apply(T obj);
}
