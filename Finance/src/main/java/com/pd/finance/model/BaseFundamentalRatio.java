package com.pd.finance.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseFundamentalRatio {
    @JsonProperty("values")
    private List<FundamentalRatioLineItem> values;

    @JsonProperty("isIncreasing")
    @Field(targetType = FieldType.BOOLEAN)
    private boolean isIncreasing;

    @JsonProperty("minPercentage")
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal minPercentage;

    @JsonProperty("maxPercentage")
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal maxPercentage;

    public BaseFundamentalRatio(List<FundamentalRatioLineItem> values) {
        this.values = values;
        this.isIncreasing = isIncreasingPattern(values);
        this.minPercentage = getMinPercentage(values);
        this.maxPercentage = getMaxPercentage(values);
    }

    private BigDecimal getMaxPercentage(List<FundamentalRatioLineItem> values) {
        BigDecimal max = null;
        for(int i=0;i<values.size();i++){

            FundamentalRatioLineItem lineItem = values.get(i);
            if(max==null || max.compareTo(lineItem.getPercentage())<0){
                max = lineItem.getPercentage();
            }
        }
        return max;
    }

    private BigDecimal getMinPercentage(List<FundamentalRatioLineItem> values) {
        BigDecimal min = null;
        for(int i=0;i<values.size();i++){

            FundamentalRatioLineItem lineItem = values.get(i);
            if(min==null || min.compareTo(lineItem.getPercentage())>0){
                min = lineItem.getPercentage();
            }
        }
        return min;
    }

    private boolean isIncreasingPattern(List<FundamentalRatioLineItem> values) {
        if(values==null || values.size()<2){
            return false;
        }
        boolean isIncreasing = true;
        for(int i=1;i<values.size();i++){
            FundamentalRatioLineItem current = values.get(i);
            FundamentalRatioLineItem previous = values.get(i-1);

            if(current.getPercentage()==null || previous.getPercentage()==null || current.getPercentage().compareTo(previous.getPercentage())<=0){
                isIncreasing = false;
                break;
            }
        }
        return isIncreasing;
    }


    public List<FundamentalRatioLineItem> getValues() {
        return values;
    }

    public void setValues(List<FundamentalRatioLineItem> values) {
        this.values = values;
    }
}
