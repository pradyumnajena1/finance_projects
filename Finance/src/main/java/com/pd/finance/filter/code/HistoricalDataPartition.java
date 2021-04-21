package com.pd.finance.filter.code;

import com.pd.finance.model.EquityHistoricalDataLineItem;
import com.pd.finance.utils.CommonUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class HistoricalDataPartition {
    private static final Logger logger = LoggerFactory.getLogger(HistoricalDataPartition.class);
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");



    private final int flatPeriodMaxLengthInDays;
    private final BigDecimal percentage;
    private final Integer allowedNumMissesInInterval;
    private final Integer allowedNumMissesInFlatPeriod;
    private int intervalLengthInDays;

    private Integer indexBeforeStartOfInterval = null;
    private Integer intervalStartLineItemIndex = null;
    private Integer intervalLastLineItemIndex = null;

    private BigDecimal lowerBound;
    private BigDecimal upperBound;
    private EquityHistoricalDataLineItem pivotLineItem;

    private List<EquityHistoricalDataLineItem> lineItems;
    private RecentPeakUpData recentPickupData;

    public HistoricalDataPartition(List<EquityHistoricalDataLineItem> lineItems, Integer intervalLengthInDays, int flatPeriodMaxLengthInDays, BigDecimal percentage, Integer allowedNumMissesInInterval, Integer allowedNumMissesInFlatPeriod) {

        this.lineItems = lineItems;
        this.intervalLengthInDays = intervalLengthInDays;
        this.flatPeriodMaxLengthInDays = flatPeriodMaxLengthInDays;
        this.percentage = percentage;
        this.allowedNumMissesInInterval =allowedNumMissesInInterval;
        this.allowedNumMissesInFlatPeriod = allowedNumMissesInFlatPeriod;
        process();


    }
    public Integer getIntervalLastLineItemIndex() {
        return intervalLastLineItemIndex;
    }

    public Integer getIntervalStartLineItemIndex() {
        return intervalStartLineItemIndex;
    }

    public Integer getIndexBeforeStartOfInterval() {
        return indexBeforeStartOfInterval;
    }

    public void process() {
        updateIndices();
        updateBounds();

        updateRecentPickupData();

    }

    protected void updateBounds() {
        if(intervalStartLineItemIndex!=null){

            pivotLineItem = lineItems.get(intervalStartLineItemIndex);
            lowerBound = CommonUtils.getLowerBound(pivotLineItem.getClose(), percentage);
            upperBound = CommonUtils.getUpperBound(pivotLineItem.getClose(), percentage);
        }

    }

    protected void updateIndices() {
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
    }

    public void updateRecentPickupData( ){


        this.recentPickupData =  new RecentPeakUpData();

        boolean allBoundaryIndicesAvailable = isAllBoundaryIndicesAvailable();
        this.recentPickupData.setAllBoundaryIndicesAvailable(allBoundaryIndicesAvailable);
        if(!allBoundaryIndicesAvailable){
            return  ;
        }
        boolean monotonicallyIncreasingBetweenInterval = isStrictlyIncreasingBetweenInterval();
        this.recentPickupData.setStrictlyIncreasingBetweenInterval(monotonicallyIncreasingBetweenInterval);
        if(!monotonicallyIncreasingBetweenInterval){

            return  ;
        }
        int flatBeforeInterval = isFlatBeforeInterval(flatPeriodMaxLengthInDays, percentage);
        this.recentPickupData.setNumViolations(flatBeforeInterval);

        return;


    }

    public boolean isAllBoundaryIndicesAvailable() {
        if (this.getIntervalLastLineItemIndex() == null || this.getIntervalStartLineItemIndex() == null || this.getIndexBeforeStartOfInterval() == null) {
            return false;
        }
        return true;
    }

    public boolean isStrictlyIncreasingBetweenInterval( ) {
        try {
            int numMisses = 0;
            for (int currentIndex = getIntervalLastLineItemIndex(); currentIndex > getIntervalStartLineItemIndex(); currentIndex--) {

                EquityHistoricalDataLineItem lineItem = lineItems.get(currentIndex);
                EquityHistoricalDataLineItem previousLineItem = lineItems.get(currentIndex - 1);

                if (lineItem.getClose().compareTo(previousLineItem.getClose()) <= 0) {
                    numMisses++;
                }
            }
            return numMisses<= allowedNumMissesInInterval;
        } catch (Exception exception) {
            logger.error(exception.getMessage());
            return false;
        }
    }
    public int isFlatBeforeInterval(Integer flatPeriodMaxLengthInDays,BigDecimal percentage) {
        try {
            List<EquityHistoricalDataLineItem> violatingLineItems = collectViolatingLineItems(flatPeriodMaxLengthInDays, percentage);
            int numViolatingItems = violatingLineItems.size();

            return numViolatingItems  ;
        } catch (Exception exception) {
            logger.error(exception.getMessage());
            return 0;
        }
    }

    @NotNull
    protected List<EquityHistoricalDataLineItem> collectViolatingLineItems(Integer flatPeriodMaxLengthInDays, BigDecimal percentage) {
        int endIndex = flatPeriodMaxLengthInDays!=null? Math.max(0,intervalStartLineItemIndex-flatPeriodMaxLengthInDays):0;
        int startIndex = indexBeforeStartOfInterval;

        List<EquityHistoricalDataLineItem> violatingLineItems = new ArrayList<>();
        for (int currentIndex = startIndex; currentIndex >= endIndex; currentIndex--) {

            EquityHistoricalDataLineItem lineItem = lineItems.get(currentIndex);
            if (lineItem.isAllFieldsAvailable() && !lineItem.isWithinRange(lowerBound, upperBound)) {
                violatingLineItems.add(lineItem);

            }
        }
        return violatingLineItems;
    }




    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HDPartition{");
        try {
            sb.append("intLength=").append(intervalLengthInDays);
            sb.append(", BS=").append(indexBeforeStartOfInterval);
            sb.append(", S=").append(intervalStartLineItemIndex);
            sb.append(", L=").append(intervalLastLineItemIndex);
            sb.append(", pi=").append(pivotLineItem);
            sb.append(", lb=").append(DECIMAL_FORMAT.format( lowerBound.doubleValue()));
            sb.append(", ub=").append(DECIMAL_FORMAT.format( upperBound.doubleValue()));
            sb.append(", pickupData=").append(recentPickupData);
        } catch (Exception exception) {
           //ignore
        }
        sb.append('}');
        return sb.toString();
    }

    public boolean isRecentPeakUpEquity() {
        return recentPickupData.isRecentPeakUpEquity(allowedNumMissesInFlatPeriod);
    }
}
