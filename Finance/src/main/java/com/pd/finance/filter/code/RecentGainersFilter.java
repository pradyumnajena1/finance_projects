package com.pd.finance.filter.code;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pd.finance.filter.EquityFilter;
import com.pd.finance.filter.FilterType;
import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityHistoricalData;
import com.pd.finance.model.EquityHistoricalDataLineItem;
import com.pd.finance.model.EquityHistoricalIntervalData;
import com.pd.finance.service.HistoricalDataInterval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class RecentGainersFilter implements EquityFilter {
    private static final Logger logger = LoggerFactory.getLogger(RecentGainersFilter.class);

    public static final int DefaultFlatPeriodMaxLengthInDays = 1500;
    public static final int DefaultAllowedNumMissesInInterval = 2;
    public static final int DefaultAllowedNumMissesInFlatPeriod = 10;

    private HistoricalDataInterval peakUpInterval = HistoricalDataInterval.OneWeek;

    private BigDecimal boundPercentage = BigDecimal.valueOf(0.05d);

    private Integer flatPeriodMaxLengthInDays  = DefaultFlatPeriodMaxLengthInDays;
    private Integer allowedNumMissesInInterval = DefaultAllowedNumMissesInInterval;
    private Integer allowedNumMissesInFlatPeriod = DefaultAllowedNumMissesInFlatPeriod;

    public HistoricalDataInterval getPeakUpInterval() {
        return peakUpInterval;
    }

    public void setPeakUpInterval(HistoricalDataInterval peakUpInterval) {
        this.peakUpInterval = peakUpInterval;
    }

    public BigDecimal getBoundPercentage() {
        return boundPercentage;
    }

    public void setBoundPercentage(BigDecimal boundPercentage) {
        this.boundPercentage = boundPercentage;
    }

    public Integer getFlatPeriodMaxLengthInDays() {
        return flatPeriodMaxLengthInDays;
    }

    public void setFlatPeriodMaxLengthInDays(Integer flatPeriodMaxLengthInDays) {
        this.flatPeriodMaxLengthInDays = flatPeriodMaxLengthInDays;
    }

    public Integer getAllowedNumMissesInInterval() {
        return allowedNumMissesInInterval;
    }

    public void setAllowedNumMissesInInterval(Integer allowedNumMissesInInterval) {
        this.allowedNumMissesInInterval = allowedNumMissesInInterval;
    }

    public Integer getAllowedNumMissesInFlatPeriod() {
        return allowedNumMissesInFlatPeriod;
    }

    public void setAllowedNumMissesInFlatPeriod(Integer allowedNumMissesInFlatPeriod) {
        this.allowedNumMissesInFlatPeriod = allowedNumMissesInFlatPeriod;
    }

    @Override
    @JsonIgnore
    public FilterType getFilterType() {
        return FilterType.InCode;
    }

    @Override
    public Criteria getCriteria(String parentObject) {
        return null;
    }

    @Override
    public boolean apply(Equity equity) {
        boolean result = false;

        result = isRecentPeakUpEquity(equity, peakUpInterval, boundPercentage);


        return result;
    }

    protected boolean isRecentPeakUpEquity(Equity equity, HistoricalDataInterval interval, BigDecimal percentage) {

        try {
            EquityHistoricalData historicalData = equity.getHistoricalData();
            if(historicalData==null){
                return false;
            }
            EquityHistoricalIntervalData intervalData = historicalData.getIntervalData(HistoricalDataInterval.OneDay);
            if(intervalData==null){
                return false;
            }
            List<EquityHistoricalDataLineItem> lineItems = intervalData.getLineItems();
            Collections.sort(lineItems);
            Integer intervalLengthInDays = getIntervalLengthInDays(interval);

            HistoricalDataPartition historicalDataPartition = new HistoricalDataPartition(lineItems,intervalLengthInDays,flatPeriodMaxLengthInDays,percentage,allowedNumMissesInInterval,allowedNumMissesInFlatPeriod);
            logger.info("isRecentPeakUp equity: {} Partition: {} ",equity.getDefaultEquityIdentifier(),historicalDataPartition);
            return historicalDataPartition.isRecentPeakUpEquity();

        } catch (Exception exception) {
            logger.error(exception.getMessage());
            return false;
        }
    }


    private int getIntervalLengthInDays(HistoricalDataInterval interval) {
        switch (interval){
            case OneWeek: return 7;
            case OneMonth: return 30;
            case ThreeMonths: return 90;
            default:
                throw new IllegalArgumentException("Interval not supported");
        }

    }







}
