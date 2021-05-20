package com.pd.finance.filter.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pd.finance.filter.EquityFilter;
import com.pd.finance.filter.FilterType;
import com.pd.finance.model.Equity;
import com.pd.finance.utils.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EquityExchangeFilter implements EquityFilter {
    public static final List<String> DefaultExchanges = List.of(Constants.EXCHANGE_BSE, Constants.EXCHANGE_NSI);
    private List<String> exchanges = DefaultExchanges;

    @Override
    @JsonIgnore
    public FilterType getFilterType() {
        return FilterType.InDb;
    }

    @Override
    public Criteria getCriteria(String parentObject) {
       
        return Criteria.where("stockExchangeDetails.exchange").in(exchanges);
    }

    @Override
    public boolean apply(Equity obj) {
        return false;
    }

    public List<String> getExchanges() {
        return exchanges;
    }

    public void setExchanges(List<String> exchanges) {
        if(CollectionUtils.isEmpty(exchanges)){
            exchanges = DefaultExchanges;
        }
        this.exchanges = exchanges;
    }
}
