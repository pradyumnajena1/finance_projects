package com.pd.finance.filter.db;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pd.finance.filter.EquityFilter;
import com.pd.finance.filter.FilterType;
import com.pd.finance.model.Equity;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Date;

public class EquityUpdateDateFilter implements EquityFilter {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Date updatedBefore;

    public Date getUpdatedBefore() {
        return updatedBefore;
    }

    public void setUpdatedBefore(Date updatedBefore) {
        this.updatedBefore = updatedBefore;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.InDb;
    }

    @Override
    public Criteria getCriteria(String parentObject) {
        return Criteria.where("updatedDate").lt(updatedBefore);
    }

    @Override
    public boolean apply(Equity obj) {
        return false;
    }
}
