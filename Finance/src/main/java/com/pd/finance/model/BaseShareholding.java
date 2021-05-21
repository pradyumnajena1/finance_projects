package com.pd.finance.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseShareholding {
   @JsonProperty("shareholdingPattern")
   private List<ShareholdingLineItem> shareholdingPattern;

    @JsonProperty("isIncreasing")
    @Field(targetType = FieldType.BOOLEAN)
    private boolean isIncreasing;

    @JsonProperty("minPercentage")
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal minPercentage;

    @JsonProperty("maxPercentage")
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal maxPercentage;

    public BaseShareholding(List<ShareholdingLineItem> shareholdingPatterns) {
        this.shareholdingPattern = shareholdingPatterns;
        this.isIncreasing = isIncreasingPattern(shareholdingPatterns);
        this.minPercentage = getMinPercentage(shareholdingPatterns);
        this.maxPercentage = getMaxPercentage(shareholdingPatterns);
    }

    private BigDecimal getMaxPercentage(List<ShareholdingLineItem> shareholdingPatterns) {
        BigDecimal max = null;
        for(int i=0;i<shareholdingPatterns.size();i++){

            ShareholdingLineItem lineItem = shareholdingPatterns.get(i);
            if(max==null || max.compareTo(lineItem.getPercentage())<0){
                max = lineItem.getPercentage();
            }
        }
        return max;
    }

    private BigDecimal getMinPercentage(List<ShareholdingLineItem> shareholdingPatterns) {
        BigDecimal min = null;
        for(int i=0;i<shareholdingPatterns.size();i++){

            ShareholdingLineItem lineItem = shareholdingPatterns.get(i);
            if(min==null || min.compareTo(lineItem.getPercentage())>0){
                min = lineItem.getPercentage();
            }
        }
        return min;
    }

    private boolean isIncreasingPattern(List<ShareholdingLineItem> shareholdingPatterns) {
        boolean isIncreasing = true;
        for(int i=1;i<shareholdingPatterns.size();i++){
            ShareholdingLineItem current = shareholdingPatterns.get(i);
            ShareholdingLineItem previous = shareholdingPatterns.get(i-1);

            if(current.getPercentage()==null || previous.getPercentage()==null || current.getPercentage().compareTo(previous.getPercentage())<=0){
                isIncreasing = false;
                break;
            }
        }
        return isIncreasing;
    }

    public List<ShareholdingLineItem> getShareholdingPattern() {
        return shareholdingPattern;
    }

    public void setShareholdingPattern(List<ShareholdingLineItem> shareholdingPattern) {
        this.shareholdingPattern = shareholdingPattern;
    }
}
