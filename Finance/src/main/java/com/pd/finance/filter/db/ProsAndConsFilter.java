package com.pd.finance.filter.db;

import com.pd.finance.filter.EquityFilter;
import com.pd.finance.filter.FilterType;
import com.pd.finance.model.Equity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.List;

public class ProsAndConsFilter  extends AbstractDBFilter<Equity> implements EquityFilter {
    private static final Logger logger = LoggerFactory.getLogger(ProsAndConsFilter.class);


    private Integer minPros   ;

    private Integer maxCons  ;


    public Integer getMinPros() {
        return minPros;
    }

    public void setMinPros(Integer minPros) {
        this.minPros = minPros;
    }

    public Integer getMaxCons() {
        return maxCons;
    }

    public void setMaxCons(Integer maxCons) {
        this.maxCons = maxCons;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.InDb;
    }

    @Override
    public Criteria getCriteria(String parentObject) {
        List<Criteria> criteriaList = new ArrayList<>();
        if(minPros!=null){

            criteriaList.add(Criteria.where("prosAndConsDetails.pros."+(this.minPros-1)).exists(true));
        }

        if(maxCons!=null){

            criteriaList.add(Criteria.where("prosAndConsDetails.cons."+(this.maxCons)).exists(false));
        }


        return getAsCompositeCriteria(criteriaList);
    }

    @Override
    public boolean apply(Equity obj) {
        return false;
    }
}
