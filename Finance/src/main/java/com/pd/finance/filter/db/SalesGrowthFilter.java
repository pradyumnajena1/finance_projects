package com.pd.finance.filter.db;

import com.pd.finance.filter.FilterType;
import com.pd.finance.filter.IFilter;
import com.pd.finance.model.CompoundedSalesGrowthDetails;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.core.query.Criteria;

import java.math.BigDecimal;

public class SalesGrowthFilter extends AbstractProfitLossFilter implements IFilter<CompoundedSalesGrowthDetails> {

    private BigDecimal minTTMGrowth;

    private String fieldName = "salesGrowthDetails";
    private String lastOneYearAttributeName = "ttm";

    @Override
    public boolean apply(CompoundedSalesGrowthDetails obj) {
        return false;
    }

    @Override
    protected BigDecimal getMinLastOneYearGrowth() {
        return minTTMGrowth;
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
