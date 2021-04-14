package com.pd.finance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pd.finance.utils.CommonUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

public class BrokerResearchLineItem implements Comparable<BrokerResearchLineItem>{
    @JsonIgnore
    private static Comparator<BrokerResearchLineItem> brokerResearchLineItemComparator = Comparator
            .comparing(BrokerResearchLineItem::getDate, CommonUtils.nullSafeDateComparator)
            .thenComparing(BrokerResearchLineItem::getBrokerName, CommonUtils.nullSafeStringComparator);

    private Date date;
    private String brokerName;
    private String rating;

    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal recoPrice;

    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal targetPrice;


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public BigDecimal getRecoPrice() {
        return recoPrice;
    }

    public void setRecoPrice(BigDecimal recoPrice) {
        this.recoPrice = recoPrice;
    }

    public BigDecimal getTargetPrice() {
        return targetPrice;
    }

    public void setTargetPrice(BigDecimal targetPrice) {
        this.targetPrice = targetPrice;
    }

    @JsonIgnore
    public boolean isRecommendedPriceAvailable( ) {
        return  getRecoPrice() != null && !BigDecimal.ZERO.equals( getRecoPrice());
    }
    @JsonIgnore
    public boolean isTargetPriceAvailable( ) {
        return  getTargetPrice() != null && !BigDecimal.ZERO.equals( getTargetPrice());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BrokerResearchLineItem that = (BrokerResearchLineItem) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(brokerName, that.brokerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, brokerName);
    }

    @Override
    public int compareTo(@NotNull BrokerResearchLineItem other) {
        return brokerResearchLineItemComparator.compare(this, other);
    }


}
