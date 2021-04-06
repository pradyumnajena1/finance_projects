package com.pd.finance.request;

public class MarketLosersWebCrawlRequest extends  AbstractWebCrawlRequest{
    private String exchange;

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }
}
