package com.pd.finance.filter.db;

import com.pd.finance.filter.FilterType;
import com.pd.finance.filter.IFilter;
import com.pd.finance.model.CompoundedReturnOnEquity;
import com.pd.finance.model.EquityProfitLossDetails;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.core.query.Criteria;

import java.math.BigDecimal;

public class ReturnOnEquityFilter extends AbstractProfitLossFilter implements IFilter<EquityProfitLossDetails> {

    private BigDecimal minLastOneYearGrowth;

    private String fieldName = "returnOnEquity";
    private String lastOneYearAttributeName = "lastOneYear";

    @Override
    public boolean apply(EquityProfitLossDetails obj) {
        return false;
    }

    @Override
    protected BigDecimal getMinLastOneYearGrowth() {
        return minLastOneYearGrowth;
    }

    @NotNull
    @Override
    protected String getLastOneYearAttributeName() {
        return lastOneYearAttributeName;
    }

    @Override
    protected String getFieldName() {
        return fieldName;
    }
}
