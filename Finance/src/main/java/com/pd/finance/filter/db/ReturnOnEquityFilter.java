package com.pd.finance.filter.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pd.finance.filter.FilterType;
import com.pd.finance.filter.IFilter;
import com.pd.finance.model.CompoundedReturnOnEquity;
import com.pd.finance.model.EquityProfitLossDetails;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.query.Criteria;

import java.math.BigDecimal;

public class ReturnOnEquityFilter extends AbstractProfitLossFilter implements IFilter<EquityProfitLossDetails> {

    @JsonProperty("minLastOneYearGrowth")
    @Field(targetType = FieldType.DECIMAL128)
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
    @JsonIgnore
    protected String getLastOneYearAttributeName() {
        return lastOneYearAttributeName;
    }

    @Override
    @JsonIgnore
    protected String getFieldName() {
        return fieldName;
    }
}
