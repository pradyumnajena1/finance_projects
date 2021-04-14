package com.pd.finance.filter.db;

import com.pd.finance.filter.IFilter;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

public abstract class AbstractDBFilter<T> implements IFilter<T> {

    protected Criteria getAsCompositeCriteria(List<Criteria> criteriaList) {
        Criteria criteria = null;
        if(criteriaList.size()>1){

            criteria = new Criteria().andOperator(criteriaList.toArray( new Criteria[criteriaList.size()]));
        }else{
            criteria = criteriaList.get(0);
        }
        return criteria;
    }
}
