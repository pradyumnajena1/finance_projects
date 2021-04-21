package com.pd.finance.filter.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pd.finance.filter.FilterType;
import com.pd.finance.model.CompoundedProfitGrowthDetails;
import com.pd.finance.model.CompoundedSalesGrowthDetails;
import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityProfitLossDetails;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.core.query.Criteria;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractProfitLossFilter extends AbstractDBFilter<EquityProfitLossDetails> {

    @JsonProperty("minTenYearsGrowth")
    private BigDecimal minTenYearsGrowth;

    @JsonProperty("minFiveYearsGrowth")
    private BigDecimal minFiveYearsGrowth;

    @JsonProperty("minThreeYearsGrowth")
    private BigDecimal minThreeYearsGrowth;

    private String lastTenYears = "lastTenYears";
    private String lastFiveYears = "lastFiveYears";
    private String lastThreeYears = "lastThreeYears";

    protected List<Criteria> criteriaList = new ArrayList<>();

    public BigDecimal getMinTenYearsGrowth() {
        return minTenYearsGrowth;
    }

    public void setMinTenYearsGrowth(BigDecimal minTenYearsGrowth) {
        this.minTenYearsGrowth = minTenYearsGrowth;
    }

    public BigDecimal getMinFiveYearsGrowth() {
        return minFiveYearsGrowth;
    }

    public void setMinFiveYearsGrowth(BigDecimal minFiveYearsGrowth) {
        this.minFiveYearsGrowth = minFiveYearsGrowth;
    }

    public BigDecimal getMinThreeYearsGrowth() {
        return minThreeYearsGrowth;
    }

    public void setMinThreeYearsGrowth(BigDecimal minThreeYearsGrowth) {
        this.minThreeYearsGrowth = minThreeYearsGrowth;
    }

    public Criteria getCriteria(String parentObject) {

        if(getMinTenYearsGrowth()!=null){

            criteriaList.add(getFieldCriteria(parentObject, getSubFieldName(lastTenYears), getMinTenYearsGrowth()));
        }
        if(getMinFiveYearsGrowth()!=null){

            criteriaList.add(getFieldCriteria(parentObject, getSubFieldName(lastFiveYears), getMinFiveYearsGrowth()));
        }
        if(getMinThreeYearsGrowth()!=null){


            criteriaList.add(getFieldCriteria(parentObject, getSubFieldName(lastThreeYears), getMinThreeYearsGrowth()));
        }

        if(getMinLastOneYearGrowth() !=null){

            criteriaList.add(getFieldCriteria(parentObject, getSubFieldName(getLastOneYearAttributeName()), getMinLastOneYearGrowth()));
        }


        return getAsCompositeCriteria(criteriaList);
    }

    @NotNull
    protected String getSubFieldName(String subFieldName) {
        return "." + getFieldName() + "." + subFieldName;
    }

    protected abstract BigDecimal getMinLastOneYearGrowth()  ;

    @NotNull
    protected abstract String getLastOneYearAttributeName()  ;

    protected abstract String getFieldName();



    @NotNull
    protected Criteria getFieldCriteria(String parentObject, String s, BigDecimal minGrowth) {
        return Criteria.where(parentObject + s).exists(true).andOperator(Criteria.where(parentObject + s).gte(minGrowth));
    }

    @JsonIgnore
    public FilterType getFilterType() {
        return FilterType.InDb;
    }

    public boolean apply(EquityProfitLossDetails profitGrowthDetails) {
        return false;
    }
}
