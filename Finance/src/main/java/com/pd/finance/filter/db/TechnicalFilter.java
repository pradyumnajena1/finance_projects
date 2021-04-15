package com.pd.finance.filter.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pd.finance.filter.FilterType;
import com.pd.finance.filter.IFilter;
import com.pd.finance.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

public class TechnicalFilter  implements IFilter<TechnicalAnalysis> {
    private static final Logger logger = LoggerFactory.getLogger(TechnicalFilter.class);
    private TechnicalSummaryFilter movingAvgFilter;
    private TechnicalSummaryFilter movingAvgCrossOverFilter;
    private TechnicalSummaryFilter technicalIndicatorFilter;

    public TechnicalSummaryFilter getMovingAvgFilter() {
        return movingAvgFilter;
    }

    public void setMovingAvgFilter(TechnicalSummaryFilter movingAvgFilter) {
        this.movingAvgFilter = movingAvgFilter;
    }

    public TechnicalSummaryFilter getMovingAvgCrossOverFilter() {
        return movingAvgCrossOverFilter;
    }

    public void setMovingAvgCrossOverFilter(TechnicalSummaryFilter movingAvgCrossOverFilter) {
        this.movingAvgCrossOverFilter = movingAvgCrossOverFilter;
    }

    public TechnicalSummaryFilter getTechnicalIndicatorFilter() {
        return technicalIndicatorFilter;
    }

    public void setTechnicalIndicatorFilter(TechnicalSummaryFilter technicalIndicatorFilter) {
        this.technicalIndicatorFilter = technicalIndicatorFilter;
    }
    public List<FieldError> validate(){
        List<FieldError> errors = new ArrayList<>();
        validateMovingAvgFilter(errors);
        validateMovingAvgCrossoverFilter(errors);

        validateTechnicalIndicatorFilter(errors);
        return errors;
    }

    private void validateTechnicalIndicatorFilter(List<FieldError> errors) {
        if(technicalIndicatorFilter!=null){

            Integer maxBearish = technicalIndicatorFilter.getMaxBearish();
            if(maxBearish!=null && (maxBearish <0 || maxBearish >6)){
                errors.add(new FieldError("technicalIndicatorFilter","maxBearish","maxBearish cant be  less than 0 greater than 7"));
            }
            Integer minBullish = technicalIndicatorFilter.getMinBullish();
            if(minBullish!=null && (minBullish <0 || minBullish >6)){
                errors.add(new FieldError("technicalIndicatorFilter","minBullish","minBullish cant be  less than 0 greater than 7"));
            }
            if(minBullish!=null && maxBearish!=null&& minBullish+maxBearish > 7){
                errors.add(new FieldError("technicalIndicatorFilter","minBullish and maxBullish","minBullish and maxBullish sum cant be greater than 7"));
            }
        }
    }

    private void validateMovingAvgCrossoverFilter(List<FieldError> errors) {
        if(movingAvgCrossOverFilter!=null){

            Integer maxBearish = movingAvgCrossOverFilter.getMaxBearish();
            if(maxBearish!=null && (maxBearish <0 || maxBearish >3)){
                errors.add(new FieldError("movingAvgCrossOverFilter","maxBearish","maxBearish cant be  less than 0 greater than 3"));
            }
            Integer minBullish = movingAvgCrossOverFilter.getMinBullish();
            if(minBullish!=null && (minBullish <0 || minBullish >3)){
                errors.add(new FieldError("movingAvgCrossOverFilter","minBullish","minBullish cant be  less than 0 greater than 3"));
            }
            if(maxBearish!=null && minBullish!=null && minBullish+maxBearish > 3){
                errors.add(new FieldError("movingAvgCrossOverFilter","minBullish and maxBullish","minBullish and maxBullish sum cant be greater than 3"));
            }

        }
    }

    private void validateMovingAvgFilter(List<FieldError> errors) {
        if(movingAvgFilter!=null){

            Integer maxBearish = movingAvgFilter.getMaxBearish();
            if(maxBearish!=null && (maxBearish <0 || maxBearish >6)){
                errors.add(new FieldError("movingAvgFilter","maxBearish","maxBearish cant be  less than 0 greater than 6"));
            }
            Integer minBullish = movingAvgFilter.getMinBullish();
            if(minBullish!=null && (minBullish <0 || minBullish >6)){
                errors.add(new FieldError("movingAvgFilter","minBullish","minBullish cant be  less than 0 greater than 6"));
            }
            if(minBullish!=null && maxBearish!=null && minBullish+maxBearish > 6){
                errors.add(new FieldError("movingAvgFilter","minBullish and maxBullish","minBullish and maxBullish sum cant be greater than 6"));
            }

        }
    }

    @Override
    @JsonIgnore
    public FilterType getFilterType() {
        return FilterType.InDb;
    }

    @Override
    public Criteria getCriteria(String parentObject) {
        List<Criteria> criteriaList = new ArrayList<>();

        if(movingAvgFilter!=null){
            criteriaList.add(movingAvgFilter.getCriteria(parentObject+".movingAverages"));
        }

        if(movingAvgCrossOverFilter!=null){
            criteriaList.add(movingAvgCrossOverFilter.getCriteria(parentObject+".movingAveragesCrossOver"));
        }

        if(technicalIndicatorFilter!=null){
            criteriaList.add(technicalIndicatorFilter.getCriteria( parentObject+".technicalIndicator"));
        }

        Criteria criteria = new Criteria().andOperator(criteriaList.toArray( new Criteria[criteriaList.size()]));
        return criteria;
    }

    @Override
    public boolean apply(TechnicalAnalysis technicalAnalysis) {


            if(technicalAnalysis==null){
                return false;
            }

            TechAnalysisSummary summary = technicalAnalysis.getSummary();
            if(summary==null){
                return false;
            }

            if(movingAvgFilter!=null) {
                boolean valid = movingAvgFilter.apply(summary.getMovingAverages());
                if(!valid) {
                    logger.info("MovingAverages filter not matching returning false");
                    return false;
                }
            }

            if(movingAvgCrossOverFilter!=null) {
            boolean valid = movingAvgCrossOverFilter.apply(summary.getMovingAveragesCrossOver());
            if(!valid) {
                logger.info("movingAvgCrossOverFilter filter not matching returning false");
                return false;
            }
        }

        if(technicalIndicatorFilter!=null) {
            boolean valid = technicalIndicatorFilter.apply(summary.getTechnicalIndicator());
            if(!valid) {
                logger.info("TechnicalIndicator filter not matching returning false");
                return false;
            }
        }


        return true;
    }
}
