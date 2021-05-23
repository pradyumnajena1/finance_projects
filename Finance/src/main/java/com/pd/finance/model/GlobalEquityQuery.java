package com.pd.finance.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mysema.query.annotations.QueryEntity;
import com.pd.finance.request.EquitySearchRequest;
import com.pd.finance.utils.JsonUtils;
import org.joda.time.Days;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@QueryEntity
@Document
public class GlobalEquityQuery {
    @Transient
    private static final Logger logger = LoggerFactory.getLogger(GlobalEquityQuery.class);

    @Transient
    public static final String SEQUENCE_NAME = "global_query_sequence";

    @Id
    private Long id;

    @NotNull
    private EquitySearchRequest searchRequest;

    @Field(targetType = FieldType.INT64)
    private BigInteger numExecutions = BigInteger.ZERO;

    @Field(targetType = FieldType.INT64)
    private BigInteger numOneStarRatings= BigInteger.ZERO;

    @Field(targetType = FieldType.INT64)
    private BigInteger numTwoStarRatings= BigInteger.ZERO;

    @Field(targetType = FieldType.INT64)
    private BigInteger numThreeStarRatings= BigInteger.ZERO;

    @Field(targetType = FieldType.INT64)
    private BigInteger numFourStarRatings= BigInteger.ZERO;

    @Field(targetType = FieldType.INT64)
    private BigInteger numFiveStarRatings= BigInteger.ZERO;

    @Field(targetType = FieldType.INT64)
    private BigInteger totalNumRatings= BigInteger.ZERO;

    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal avgRatings = BigDecimal.ZERO;



    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal avgNumExecutionsPerDay = BigDecimal.ZERO;

    private Date lastExecutionDate;
    private Date updatedDate;
    private Date createdDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EquitySearchRequest getSearchRequest() {
        return searchRequest;
    }

    public void setSearchRequest(EquitySearchRequest searchRequest) {
        this.searchRequest = searchRequest;
    }

    public BigInteger getNumExecutions() {
        return numExecutions;
    }

    public void setNumExecutions(BigInteger numExecutions) {
        this.numExecutions = numExecutions;
    }

    public BigInteger getNumOneStarRatings() {
        return numOneStarRatings;
    }

    public void setNumOneStarRatings(BigInteger numOneStarRatings) {
        this.numOneStarRatings = numOneStarRatings;
    }

    public BigInteger getNumTwoStarRatings() {
        return numTwoStarRatings;
    }

    public void setNumTwoStarRatings(BigInteger numTwoStarRatings) {
        this.numTwoStarRatings = numTwoStarRatings;
    }

    public BigInteger getNumThreeStarRatings() {
        return numThreeStarRatings;
    }

    public void setNumThreeStarRatings(BigInteger numThreeStarRatings) {
        this.numThreeStarRatings = numThreeStarRatings;
    }

    public BigInteger getNumFourStarRatings() {
        return numFourStarRatings;
    }

    public void setNumFourStarRatings(BigInteger numFourStarRatings) {
        this.numFourStarRatings = numFourStarRatings;
    }

    public BigInteger getNumFiveStarRatings() {
        return numFiveStarRatings;
    }

    public void setNumFiveStarRatings(BigInteger numFiveStarRatings) {
        this.numFiveStarRatings = numFiveStarRatings;
    }

    public BigInteger getTotalNumRatings() {
        return totalNumRatings;
    }

    public void setTotalNumRatings(BigInteger totalNumRatings) {
        this.totalNumRatings = totalNumRatings;
    }

    public BigDecimal getAvgRatings() {
        return avgRatings;
    }

    public void setAvgRatings(BigDecimal avgRatings) {
        this.avgRatings = avgRatings;
    }

    public BigDecimal getAvgNumExecutionsPerDay() {
        return avgNumExecutionsPerDay;
    }

    public void setAvgNumExecutionsPerDay(BigDecimal avgNumExecutionsPerDay) {
        this.avgNumExecutionsPerDay = avgNumExecutionsPerDay;
    }

    public Date getLastExecutionDate() {
        return lastExecutionDate;
    }

    public void setLastExecutionDate(Date lastExecutionDate) {
        this.lastExecutionDate = lastExecutionDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    public void recordLatestExecutionTime(){
        setLastExecutionDate(new Date());
        setNumExecutions(getNumExecutions().add(BigInteger.ONE));
        LocalDate createdLocalDate = Instant.ofEpochMilli(createdDate.getTime()).atZone(ZoneId.systemDefault())
                .toLocalDate();
        long noOfDaysBetween = ChronoUnit.DAYS.between(createdLocalDate,LocalDate.now())+1;
        setAvgNumExecutionsPerDay(BigDecimal.valueOf(getNumExecutions().longValue()).divide(BigDecimal.valueOf(noOfDaysBetween), RoundingMode.HALF_UP));
    }

    public void updateStarRating(int numStars ) {
        switch (numStars){
            case 1:
                incrementOneStarRating();
                break;
            case 2:
                incrementTwoStarRating();
                break;
            case 3:
                incrementThreeStarRating();
                break;
            case 4:
                incrementFourStarRating();
                break;
            case 5:
                incrementFiveStarRating();
                break;

        }

    }

    private void incrementFiveStarRating() {
        setNumFiveStarRatings( getNumFiveStarRatings().add(BigInteger.ONE));
        setTotalNumRatings(getTotalNumRatings().add(BigInteger.ONE));
        updateAvgRating();
    }
    private void updateAvgRating(){
        BigInteger totalStars = BigInteger.ZERO;
        totalStars = totalStars.add(getNumOneStarRatings());
        totalStars = totalStars.add(getNumTwoStarRatings().multiply(BigInteger.TWO));
        totalStars = totalStars.add(getNumThreeStarRatings().multiply(BigInteger.valueOf(3)));
        totalStars = totalStars.add(getNumFourStarRatings().multiply(BigInteger.valueOf(4)));
        totalStars = totalStars.add(getNumFiveStarRatings().multiply(BigInteger.valueOf(5)));
        BigDecimal numerator = BigDecimal.valueOf(totalStars.longValue()).setScale(1);
        BigDecimal denom = BigDecimal.valueOf(getTotalNumRatings().longValue()).setScale(1);
        BigDecimal newAvgRating =  numerator.divide(denom,RoundingMode.HALF_UP);
        setAvgRatings(newAvgRating);
    }

    private void incrementFourStarRating() {
        setNumFourStarRatings( getNumFourStarRatings().add(BigInteger.ONE));
        setTotalNumRatings(getTotalNumRatings().add(BigInteger.ONE));
        updateAvgRating();
    }

    private void incrementThreeStarRating() {
        setNumThreeStarRatings( getNumThreeStarRatings().add(BigInteger.ONE));
        setTotalNumRatings(getTotalNumRatings().add(BigInteger.ONE));
        updateAvgRating();
    }

    private void incrementTwoStarRating() {
        setNumTwoStarRatings( getNumTwoStarRatings().add(BigInteger.ONE));
        setTotalNumRatings(getTotalNumRatings().add(BigInteger.ONE));
        updateAvgRating();
    }

    private void incrementOneStarRating() {
        setNumOneStarRatings( getNumOneStarRatings().add(BigInteger.ONE));
        setTotalNumRatings(getTotalNumRatings().add(BigInteger.ONE));
        updateAvgRating();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GlobalEquityQuery{");
        String serialize = null;
        try {
            serialize = JsonUtils.serialize(searchRequest);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
        sb.append("searchRequest=").append(serialize);
        sb.append('}');
        return sb.toString();
    }
}
