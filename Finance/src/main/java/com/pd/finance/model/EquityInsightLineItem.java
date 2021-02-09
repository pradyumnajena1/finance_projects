package com.pd.finance.model;

public class EquityInsightLineItem {


    private String insight;
    private StockMarketSentiments marketSentiments;

    public String getInsight() {
        return insight;
    }

    public void setInsight(String insight) {
        this.insight = insight;
    }

    public StockMarketSentiments getMarketSentiments() {
        return marketSentiments;
    }

    public void setMarketSentiments(StockMarketSentiments marketSentiments) {
        this.marketSentiments = marketSentiments;
    }
}
