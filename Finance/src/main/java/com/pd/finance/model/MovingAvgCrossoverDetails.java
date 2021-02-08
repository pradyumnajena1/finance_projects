package com.pd.finance.model;

import java.util.List;

public class MovingAvgCrossoverDetails {
    private List<MovingAvgCrossoverLineItem> lineItems;

    public List<MovingAvgCrossoverLineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<MovingAvgCrossoverLineItem> lineItems) {
        this.lineItems = lineItems;
    }
}
