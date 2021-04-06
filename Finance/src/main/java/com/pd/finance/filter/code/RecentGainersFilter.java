package com.pd.finance.filter.code;

import com.pd.finance.filter.EquityFilter;
import com.pd.finance.filter.FilterType;
import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityHistoricalDataLineItem;
import com.pd.finance.model.EquityHistoricalIntervalData;
import com.pd.finance.service.HistoricalDataInterval;
import org.springframework.data.mongodb.core.query.Criteria;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class RecentGainersFilter implements EquityFilter {

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

        EquityHistoricalIntervalData intervalData = equity.getHistoricalData().getIntervalData(HistoricalDataInterval.OneDay);
        List<EquityHistoricalDataLineItem> lineItems = intervalData.getLineItems();

        Collections.sort(lineItems);

        Integer intervalLastLineItemIndex = null;
        Integer intervalStartLineItemIndex = null;
        Integer intervalLengthInDays = getIntervalLengthInDays(interval);

        Integer indexBeforeStartOfInterval = null;

        for (int currentIndex = lineItems.size() - 1; currentIndex >= 0; currentIndex--) {
            EquityHistoricalDataLineItem lineItem = lineItems.get(currentIndex);
            if (lineItem.isAllFieldsAvailable()) {

                if (intervalLastLineItemIndex != null && intervalStartLineItemIndex != null) {
                    indexBeforeStartOfInterval = currentIndex;
                    break;
                }

                if (intervalLastLineItemIndex == null) {
                    intervalLastLineItemIndex = currentIndex;
                    continue;
                }

                if (intervalStartLineItemIndex == null && (intervalLastLineItemIndex - currentIndex) >= intervalLengthInDays) {
                    intervalStartLineItemIndex = currentIndex;
                    continue;
                }

            }

        }
        if (intervalLastLineItemIndex == null || intervalStartLineItemIndex == null || indexBeforeStartOfInterval == null) {
            return false;
        }


        if (!isMonotonicallyIncreasingBetweenInterval(lineItems, intervalLastLineItemIndex, intervalStartLineItemIndex)) {

            return false;
        }

        if (!isFlatBeforeInterval(percentage, lineItems, intervalStartLineItemIndex, indexBeforeStartOfInterval)) {

            return false;

        }
        return true;
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
        for (int currentIndex = lastLineItemIndex; currentIndex > secondLastLineItemIndex; currentIndex--) {

            EquityHistoricalDataLineItem lineItem = lineItems.get(currentIndex);
            EquityHistoricalDataLineItem previousLineItem = lineItems.get(currentIndex - 1);

            if (lineItem.getClose().compareTo(previousLineItem.getClose()) < 0) {
                return false;
            }
        }
        return true;
    }


    private boolean isFlatBeforeInterval(BigDecimal percentage, List<EquityHistoricalDataLineItem> lineItems, Integer intervalStartLineItemIndex, Integer indexBeforeStartOfInterval) {
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
    }


}
