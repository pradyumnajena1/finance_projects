package com.pd.finance.filter;

import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CompositeIncodeFilter<T> implements IFilter<T>{

    private List<IFilter<T>> filters;

    private String operator;

    public CompositeIncodeFilter(List<IFilter<T>> filters, String operator) {
        if(!( "And".equalsIgnoreCase(operator) || "Or".equalsIgnoreCase(operator))){
            throw new IllegalArgumentException("operator must be And or Or");
        }

        this.filters = new ArrayList<>( filters);
        this.operator = operator.toUpperCase();

    }

    @Override
    public FilterType getFilterType() {
        return FilterType.InCode;
    }

    @Override
    public Criteria getCriteria(String parentObject) {
        return null;
    }

    @Override
    public boolean apply(T obj) {

        if("And".equalsIgnoreCase(operator)){
            Optional<IFilter<T>> first = filters.parallelStream().filter(filter -> !filter.apply(obj)).findFirst();
            if(first.isPresent()){
                return false;
            }
            return true;
        }else {
            boolean result = false;
            List<IFilter<T>> successfulFilters = filters.parallelStream().filter(filter -> filter.apply(obj)).collect(Collectors.toList());
            return successfulFilters.size()!=0;
        }


    }
}
