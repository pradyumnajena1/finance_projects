package com.pd.finance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;
import java.util.TreeSet;

public class BrokerResearchDetails extends EquityAttribute {
    @JsonIgnore
    private static Set<String> POSITIVE_RATINGS = Set.of("BUY", "ACCUMULATE");
    @JsonIgnore
    private static final Set<String> NEGATIVE_RATINGS = Set.of("REDUCE", "SELL");
    @JsonIgnore
    private static Set<String> NEUTRAL_RATINGS = Set.of("HOLD", "NEUTRAL");

    @Field(targetType = FieldType.INT32)
    private int numPositives;

    @Field(targetType = FieldType.INT32)
    private int numNegatives;

    @Field(targetType = FieldType.INT32)
    private int numNeutrals;

    @Field(targetType = FieldType.INT32)
    private int totalNumReviews;

    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal avgGainPercentage;


    private Set<BrokerResearchLineItem> brokerResearchLineItems = new TreeSet<>();

    public Set<BrokerResearchLineItem> getBrokerResearchLineItems() {
        return brokerResearchLineItems;
    }

    public void setBrokerResearchLineItems(Set<BrokerResearchLineItem> brokerResearchLineItems) {
        if (CollectionUtils.isNotEmpty(brokerResearchLineItems)) {
            this.brokerResearchLineItems = new TreeSet<>(brokerResearchLineItems);
            computeStats();
        }

    }

    private void computeStats() {
        int numPositives = 0;
        int numNegatives = 0;
        int numNeutrals = 0;
        this.totalNumReviews = brokerResearchLineItems.size();
        BigDecimal totalRecommendedPrice = BigDecimal.ZERO;
        BigDecimal totalTargetPrice = BigDecimal.ZERO;
        for (BrokerResearchLineItem lineItem : brokerResearchLineItems) {

            String rating = lineItem.getRating();
            if (StringUtils.isNotBlank(rating)) {
                if (isPositiveRating(rating)) {
                    numPositives++;
                } else if (isNegativeRating(rating)) {
                    numNegatives++;
                } else if (isNeutralRating(rating)) {
                    numNeutrals++;
                }
            }
            if (lineItem.isRecommendedPriceAvailable() && lineItem.isTargetPriceAvailable()) {
                totalTargetPrice =  totalTargetPrice.add(lineItem.getTargetPrice());
                totalRecommendedPrice =  totalRecommendedPrice.add(lineItem.getRecoPrice());
            }

        }
        this.numPositives = numPositives;
        this.numNegatives = numNegatives;
        this.numNeutrals = numNeutrals;
        if (totalRecommendedPrice.compareTo(BigDecimal.ZERO) > 0 && totalTargetPrice.compareTo(BigDecimal.ZERO) > 0) {

            BigDecimal diff = totalTargetPrice.subtract(totalRecommendedPrice);
            this.avgGainPercentage = diff.divide(totalRecommendedPrice, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
        }

    }


    private boolean isNeutralRating(String rating) {
        if (StringUtils.isBlank(rating)) {
            return false;
        }
        return NEUTRAL_RATINGS.contains(rating.toUpperCase());
    }

    private boolean isNegativeRating(String rating) {
        if (StringUtils.isBlank(rating)) {
            return false;
        }
        return NEGATIVE_RATINGS.contains(rating.toUpperCase());
    }

    private boolean isPositiveRating(String rating) {
        if (StringUtils.isBlank(rating)) {
            return false;
        }
        return POSITIVE_RATINGS.contains(rating.toUpperCase());
    }

    public int getNumPositives() {
        return numPositives;
    }

    public void setNumPositives(int numPositives) {
        this.numPositives = numPositives;
    }

    public int getNumNegatives() {
        return numNegatives;
    }

    public void setNumNegatives(int numNegatives) {
        this.numNegatives = numNegatives;
    }

    public int getNumNeutrals() {
        return numNeutrals;
    }

    public void setNumNeutrals(int numNeutrals) {
        this.numNeutrals = numNeutrals;
    }

    public int getTotalNumReviews() {
        return totalNumReviews;
    }

    public void setTotalNumReviews(int totalNumReviews) {
        this.totalNumReviews = totalNumReviews;
    }

    public BigDecimal getAvgGainPercentage() {
        return avgGainPercentage;
    }

    public void setAvgGainPercentage(BigDecimal avgGainPercentage) {
        this.avgGainPercentage = avgGainPercentage;
    }
}
