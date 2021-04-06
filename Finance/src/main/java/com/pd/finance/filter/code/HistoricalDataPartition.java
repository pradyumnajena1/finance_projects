package com.pd.finance.filter.code;

import com.pd.finance.model.EquityHistoricalDataLineItem;

import java.util.List;

public class HistoricalDataPartition {

    private Integer intervalLastLineItemIndex = null;
    private Integer intervalStartLineItemIndex = null;
    private Integer indexBeforeStartOfInterval = null;

    private List<EquityHistoricalDataLineItem> lineItems;
    private int intervalLengthInDays;

    public HistoricalDataPartition(List<EquityHistoricalDataLineItem> lineItems, int intervalLengthInDays) {
        this.lineItems = lineItems;
        this.intervalLengthInDays = intervalLengthInDays;

    }

    public void process() {
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

    public boolean isAllBoundaryIndicesAvailable() {
        if (this.getIntervalLastLineItemIndex() == null || this.getIntervalStartLineItemIndex() == null || this.getIndexBeforeStartOfInterval() == null) {
            return false;
        }
        return true;
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
}
