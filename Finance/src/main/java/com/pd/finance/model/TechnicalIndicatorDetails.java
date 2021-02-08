package com.pd.finance.model;

import java.util.List;

public class TechnicalIndicatorDetails {
    private List<TechIndicatorLineItem> lineItems;

    public List<TechIndicatorLineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<TechIndicatorLineItem> lineItems) {
        this.lineItems = lineItems;
    }
}
