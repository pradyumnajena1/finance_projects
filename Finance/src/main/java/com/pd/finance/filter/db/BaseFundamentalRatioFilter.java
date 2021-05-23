package com.pd.finance.filter.db;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pd.finance.filter.EquityFilter;
import com.pd.finance.filter.FilterType;
import com.pd.finance.model.Equity;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.query.Criteria;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BaseFundamentalRatioFilter extends AbstractDBFilter<Equity>  implements EquityFilter {
    @JsonProperty("increasing")
    private Boolean increasing;

    @JsonProperty("min")
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal min;

    @JsonProperty("max")
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal max;

    public Boolean getIncreasing() {
        return increasing;
    }

    public void setIncreasing(Boolean increasing) {
        this.increasing = increasing;
    }

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }
    @Override
    public FilterType getFilterType() {
        return FilterType.InDb;
    }

    @Override
    public Criteria getCriteria(String parentObject) {
        List<Criteria> criteriaList = new ArrayList<>();

        if(getIncreasing()!=null){
            criteriaList.add(Criteria.where( parentObject+ ".isIncreasing").is(getIncreasing()));
        }
        if(getMin()!=null){
            criteriaList.add(Criteria.where(parentObject+".minPercentage").gte(getMin()));
        }
        if(getMax()!=null){
            criteriaList.add(Criteria.where(parentObject+".maxPercentage").lte(getMax()));
        }

        return getAsCompositeCriteria(criteriaList);
    }

    @Override
    public boolean apply(Equity obj) {
        return false;
    }
}
