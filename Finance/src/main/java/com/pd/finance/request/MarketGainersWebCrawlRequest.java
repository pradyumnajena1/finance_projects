package com.pd.finance.request;

import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

public class MarketGainersWebCrawlRequest extends  AbstractWebCrawlRequest{

    private String exchange;

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }
}
