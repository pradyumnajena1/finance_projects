package com.pd.finance.filter.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BrokerResearchFilter extends AbstractDBFilter<Equity> implements EquityFilter {

    @JsonProperty("minTotalNumReviews")
    private Integer minTotalNumReviews;

    @JsonProperty("minNumPositiveReviews")
    private Integer minNumPositiveReviews;

    @JsonProperty("maxNumNegativeReviews")
    private Integer maxNumNegativeReviews;

    @JsonProperty("maxNumNeutralReviews")
    private Integer maxNumNeutralReviews;

    @JsonProperty("minAvgGainPercentage")
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal minAvgGainPercentage;

    @Override
    @JsonIgnore
    public FilterType getFilterType() {
        return FilterType.InDb;
    }

    @Override
    public Criteria getCriteria(String parentObject) {
        List<Criteria> criteriaList = new ArrayList<>();
        if(minTotalNumReviews!=null){

            criteriaList.add(Criteria.where("brokerResearchDetails.totalNumReviews" ).gte(minTotalNumReviews));
        }
        if(minNumPositiveReviews!=null){

            criteriaList.add(Criteria.where("brokerResearchDetails.numPositives").gte(minNumPositiveReviews));
        }
        if(maxNumNegativeReviews!=null){

            criteriaList.add(Criteria.where("brokerResearchDetails.numNegatives").lte(maxNumNegativeReviews));
        }
        if(maxNumNeutralReviews!=null){

            criteriaList.add(Criteria.where("brokerResearchDetails.numNeutrals").lte(maxNumNeutralReviews));
        }
        if(minAvgGainPercentage!=null){
            criteriaList.add(Criteria.where("brokerResearchDetails.avgGainPercentage").gte(minAvgGainPercentage));
        }

         return getAsCompositeCriteria(criteriaList);


    }

    @Override
    public boolean apply(Equity obj) {
        return false;
    }

    public Integer getMinTotalNumReviews() {
        return minTotalNumReviews;
    }

    public void setMinTotalNumReviews(Integer minTotalNumReviews) {
        this.minTotalNumReviews = minTotalNumReviews;
    }

    public Integer getMinNumPositiveReviews() {
        return minNumPositiveReviews;
    }

    public void setMinNumPositiveReviews(Integer minNumPositiveReviews) {
        this.minNumPositiveReviews = minNumPositiveReviews;
    }

    public Integer getMaxNumNegativeReviews() {
        return maxNumNegativeReviews;
    }

    public void setMaxNumNegativeReviews(Integer maxNumNegativeReviews) {
        this.maxNumNegativeReviews = maxNumNegativeReviews;
    }

    public Integer getMaxNumNeutralReviews() {
        return maxNumNeutralReviews;
    }

    public void setMaxNumNeutralReviews(Integer maxNumNeutralReviews) {
        this.maxNumNeutralReviews = maxNumNeutralReviews;
    }

    public BigDecimal getMinAvgGainPercentage() {
        return minAvgGainPercentage;
    }

    public void setMinAvgGainPercentage(BigDecimal minAvgGainPercentage) {
        this.minAvgGainPercentage = minAvgGainPercentage;
    }
}
