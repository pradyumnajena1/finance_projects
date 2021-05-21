package com.pd.finance.filter.db;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pd.finance.filter.EquityFilter;
import com.pd.finance.filter.FilterType;
import com.pd.finance.model.Equity;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.List;

public class BlockDealsFilter extends AbstractDBFilter<Equity> implements EquityFilter {
    @JsonProperty("minPurchases")
    private Integer minPurchases;

    @JsonProperty("maxSales")
    private Integer maxSales;

    public Integer getMinPurchases() {
        return minPurchases;
    }

    public void setMinPurchases(Integer minPurchases) {
        this.minPurchases = minPurchases;
    }

    public Integer getMaxSales() {
        return maxSales;
    }

    public void setMaxSales(Integer maxSales) {
        this.maxSales = maxSales;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.InDb;
    }

    @Override
    public Criteria getCriteria(String parentObject) {
        List<Criteria> criteriaList = new ArrayList<>();
        if(getMaxSales()!=null){
            criteriaList.add(Criteria.where(parentObject +".blockDealDetails.saleDeals."+(this.getMaxSales())).exists(false));
        }
        if(getMinPurchases()!=null){
            criteriaList.add(Criteria.where(parentObject+".blockDealDetails.purchaseDeals."+(this.getMinPurchases()-1)).exists(true));
        }

        return getAsCompositeCriteria(criteriaList);
    }

    @Override
    public boolean apply(Equity obj) {
        return false;
    }
}
