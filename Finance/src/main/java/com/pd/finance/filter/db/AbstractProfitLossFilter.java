package com.pd.finance.filter.db;

import com.pd.finance.filter.FilterType;
import com.pd.finance.model.CompoundedProfitGrowthDetails;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.core.query.Criteria;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractProfitLossFilter {


    private BigDecimal minTenYearsGrowth;
    private BigDecimal minFiveYearsGrowth;
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


        return getFinalCriteria(criteriaList);
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
    protected Criteria getFinalCriteria(List<Criteria> criteriaList) {
        Criteria criteria = null;
        if(criteriaList.size()>1){

              criteria = new Criteria().andOperator(criteriaList.toArray( new Criteria[criteriaList.size()]));
        }else{
            criteria = criteriaList.get(0);
        }
        return criteria;
    }

    @NotNull
    protected Criteria getFieldCriteria(String parentObject, String s, BigDecimal minGrowth) {
        return Criteria.where(parentObject + s).exists(true).andOperator(Criteria.where(parentObject + s).gte(minGrowth));
    }

    public FilterType getFilterType() {
        return FilterType.InDb;
    }

    public boolean apply(CompoundedProfitGrowthDetails profitGrowthDetails) {
        return false;
    }
}
