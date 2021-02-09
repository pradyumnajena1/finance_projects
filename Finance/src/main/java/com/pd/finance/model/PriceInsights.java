package com.pd.finance.model;

import java.util.List;

public class PriceInsights {

    private List<EquityInsightLineItem> lineItems;

    public List<EquityInsightLineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<EquityInsightLineItem> lineItems) {
        this.lineItems = lineItems;
    }
}
