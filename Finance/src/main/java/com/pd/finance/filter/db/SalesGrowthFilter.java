package com.pd.finance.filter.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pd.finance.filter.FilterType;
import com.pd.finance.filter.IFilter;
import com.pd.finance.model.CompoundedSalesGrowthDetails;
import com.pd.finance.model.EquityProfitLossDetails;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.query.Criteria;

import java.math.BigDecimal;

public class SalesGrowthFilter extends AbstractProfitLossFilter implements IFilter<EquityProfitLossDetails> {

    @JsonProperty("minTTMGrowth")
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal minTTMGrowth;

    private String fieldName = "salesGrowthDetails";
    private String lastOneYearAttributeName = "ttm";

    @Override
    public boolean apply(EquityProfitLossDetails obj) {
        return false;
    }

    @Override
    protected BigDecimal getMinLastOneYearGrowth() {
        return minTTMGrowth;
    }

    @NotNull
    @JsonIgnore
    @Override
    protected String getLastOneYearAttributeName() {
        return lastOneYearAttributeName;
    }

    @Override
    @JsonIgnore
    protected String getFieldName() {
        return fieldName;
    }
}
