package com.pd.finance.filter;

import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.List;

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

             for(IFilter<T> aFilter:filters){
                 if(!aFilter.apply(obj)){
                     return false;
                 }
             }
             return true;
        }else {
            boolean result = false;

            for(IFilter<T> aFilter:filters){
                 result = result || aFilter.apply(obj);
            }
            return result;
        }


    }
}
