package com.pd.finance.filter.db;

import com.pd.finance.filter.FilterType;
import com.pd.finance.filter.IFilter;
import com.pd.finance.model.CompoundedProfitGrowthDetails;
import com.pd.finance.model.EquityProfitLossDetails;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.core.query.Criteria;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProfitGrowthFilter  extends AbstractProfitLossFilter implements IFilter<EquityProfitLossDetails> {


    private BigDecimal minTTMGrowth;

    private String fieldName = "profitGrowthDetails";
    private String lastOneYearAttributeName = "ttm";


    public BigDecimal getMinTTMGrowth() {
        return minTTMGrowth;
    }

    public void setMinTTMGrowth(BigDecimal minTTMGrowth) {
        this.minTTMGrowth = minTTMGrowth;
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
