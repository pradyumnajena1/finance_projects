package com.pd.finance.model;

import java.util.List;

public class MovingAvgDetails {

    private List<MovingAverageLineItem> lineItems;

    public List<MovingAverageLineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<MovingAverageLineItem> lineItems) {
        this.lineItems = lineItems;
    }
}
