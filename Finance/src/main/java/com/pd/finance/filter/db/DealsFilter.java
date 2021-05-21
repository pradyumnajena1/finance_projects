package com.pd.finance.filter.db;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pd.finance.filter.EquityFilter;
import com.pd.finance.filter.FilterType;
import com.pd.finance.model.Equity;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.List;

public class DealsFilter extends AbstractDBFilter<Equity> implements EquityFilter {

    @JsonProperty("blockDealsFilter")
    private BlockDealsFilter blockDealsFilter;

    @JsonProperty("bulkDealsFilter")
    private BulkDealsFilter bulkDealsFilter;

    public BlockDealsFilter getBlockDealsFilter() {
        return blockDealsFilter;
    }

    public void setBlockDealsFilter(BlockDealsFilter blockDealsFilter) {
        this.blockDealsFilter = blockDealsFilter;
    }

    public BulkDealsFilter getBulkDealsFilter() {
        return bulkDealsFilter;
    }

    public void setBulkDealsFilter(BulkDealsFilter bulkDealsFilter) {
        this.bulkDealsFilter = bulkDealsFilter;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.InDb;
    }

    @Override
    public Criteria getCriteria(String parentObject) {
        List<Criteria> criteriaList = new ArrayList<>();
        if(getBlockDealsFilter()!=null){
            criteriaList.add( getBlockDealsFilter().getCriteria("equityDealsDetails") );
        }
        if(getBulkDealsFilter()!=null){
            criteriaList.add(getBulkDealsFilter().getCriteria( "equityDealsDetails"));
        }

        return getAsCompositeCriteria(criteriaList);
    }

    @Override
    public boolean apply(Equity obj) {
        return false;
    }
}
