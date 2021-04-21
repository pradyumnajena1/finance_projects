package com.pd.finance.filter.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pd.finance.filter.EquityFilter;
import com.pd.finance.filter.FilterType;
import com.pd.finance.model.Equity;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.List;

public class ProfitLossFilter extends AbstractDBFilter<Equity> implements EquityFilter {

    @JsonProperty("salesGrowthFilter")
    private SalesGrowthFilter salesGrowthFilter;

    @JsonProperty("profitGrowthFilter")
    private ProfitGrowthFilter profitGrowthFilter;

    @JsonProperty("stockPriceCagrFilter")
    private StockPriceCagrFilter stockPriceCagrFilter;

    @JsonProperty("returnOnEquityFilter")
    private ReturnOnEquityFilter returnOnEquityFilter;

    public SalesGrowthFilter getSalesGrowthFilter() {
        return salesGrowthFilter;
    }

    public void setSalesGrowthFilter(SalesGrowthFilter salesGrowthFilter) {
        this.salesGrowthFilter = salesGrowthFilter;
    }

    public ProfitGrowthFilter getProfitGrowthFilter() {
        return profitGrowthFilter;
    }

    public void setProfitGrowthFilter(ProfitGrowthFilter profitGrowthFilter) {
        this.profitGrowthFilter = profitGrowthFilter;
    }

    public StockPriceCagrFilter getStockPriceCagrFilter() {
        return stockPriceCagrFilter;
    }

    public void setStockPriceCagrFilter(StockPriceCagrFilter stockPriceCagrFilter) {
        this.stockPriceCagrFilter = stockPriceCagrFilter;
    }

    public ReturnOnEquityFilter getReturnOnEquityFilter() {
        return returnOnEquityFilter;
    }

    public void setReturnOnEquityFilter(ReturnOnEquityFilter returnOnEquityFilter) {
        this.returnOnEquityFilter = returnOnEquityFilter;
    }

    @Override
    @JsonIgnore
    public FilterType getFilterType() {
        return FilterType.InDb;
    }

    @Override
    public Criteria getCriteria(String parentObject) {

        List<Criteria> criteriaList = new ArrayList<>();
        if(getProfitGrowthFilter()!=null){
            criteriaList.add(getProfitGrowthFilter().getCriteria("profitLossDetails"));
        }
        if(getReturnOnEquityFilter()!=null){
            criteriaList.add(getReturnOnEquityFilter().getCriteria("profitLossDetails"));
        }
        if(getSalesGrowthFilter()!=null){
            criteriaList.add(getSalesGrowthFilter().getCriteria("profitLossDetails"));
        }
        if(getStockPriceCagrFilter()!=null){
            criteriaList.add(getSalesGrowthFilter().getCriteria("profitLossDetails"));
        }
        return getAsCompositeCriteria(criteriaList);
    }

    @Override
    public boolean apply(Equity obj) {
        return false;
    }
}
