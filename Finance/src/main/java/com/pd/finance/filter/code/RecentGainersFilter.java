package com.pd.finance.filter.code;

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

    private HistoricalDataInterval peakUpInterval = HistoricalDataInterval.OneWeek;

    private BigDecimal boundPercentage = BigDecimal.valueOf(0.05d);

    private Integer flatPeriodMaxLengthInDays  = DefaultFlatPeriodMaxLengthInDays;

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

    @Override
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
            if(interval==null){
                return false;
            }
            List<EquityHistoricalDataLineItem> lineItems = intervalData.getLineItems();
            Collections.sort(lineItems);
            Integer intervalLengthInDays = getIntervalLengthInDays(interval);
            HistoricalDataPartition historicalDataPartition = new HistoricalDataPartition(lineItems,intervalLengthInDays);
            historicalDataPartition.process();

            if (!historicalDataPartition.isAllBoundaryIndicesAvailable()){
                return false;
            }

            if (!isMonotonicallyIncreasingBetweenInterval(lineItems,historicalDataPartition.getIntervalLastLineItemIndex(),historicalDataPartition.getIntervalStartLineItemIndex())) {

                return false;
            }

            if (!isFlatBeforeInterval(percentage, lineItems, historicalDataPartition.getIntervalStartLineItemIndex(), historicalDataPartition.getIndexBeforeStartOfInterval())) {

                return false;

            }
            return true;
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


    private boolean isMonotonicallyIncreasingBetweenInterval(List<EquityHistoricalDataLineItem> lineItems, Integer lastLineItemIndex, Integer secondLastLineItemIndex) {
        try {
            for (int currentIndex = lastLineItemIndex; currentIndex > secondLastLineItemIndex; currentIndex--) {

                EquityHistoricalDataLineItem lineItem = lineItems.get(currentIndex);
                EquityHistoricalDataLineItem previousLineItem = lineItems.get(currentIndex - 1);

                if (lineItem.getClose().compareTo(previousLineItem.getClose()) < 0) {
                    return false;
                }
            }
            return true;
        } catch (Exception exception) {
            logger.error(exception.getMessage());
            return false;
        }
    }


    private boolean isFlatBeforeInterval(BigDecimal percentage, List<EquityHistoricalDataLineItem> lineItems, Integer intervalStartLineItemIndex, Integer indexBeforeStartOfInterval) {
        try {
            int endIndex = flatPeriodMaxLengthInDays!=null? Math.max(0,intervalStartLineItemIndex-flatPeriodMaxLengthInDays):0;
            int startIndex = indexBeforeStartOfInterval;

            for (int currentIndex = startIndex; currentIndex >= endIndex; currentIndex--) {

                EquityHistoricalDataLineItem lineItem = lineItems.get(currentIndex);
                EquityHistoricalDataLineItem otherLineItem = lineItems.get(intervalStartLineItemIndex);
                if (lineItem.isAllFieldsAvailable() && !lineItem.isWithinRange(otherLineItem, percentage)) {
                    return false;
                }
            }
            return true;
        } catch (Exception exception) {
            logger.error(exception.getMessage());
            return false;
        }
    }


}
