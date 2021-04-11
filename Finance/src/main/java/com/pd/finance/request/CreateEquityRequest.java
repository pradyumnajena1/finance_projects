package com.pd.finance.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.pd.finance.utils.Constants;
import com.pd.finance.utils.JsonUtils;

public class CreateEquityRequest {
    @JsonProperty("name")
    private String name;
    @JsonProperty("srcUrl")
    private String srcUrl;
    @JsonProperty("exchange")
    private String exchange = Constants.EXCHANGE_NSI;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSrcUrl() {
        return srcUrl;
    }

    public void setSrcUrl(String srcUrl) {
        this.srcUrl = srcUrl;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    @Override
    public String toString() {
        String serialize = "";
        try {
              serialize = JsonUtils.serialize(this);

        } catch (JsonProcessingException e) {
            //ignore
        }
        return serialize;
    }
}
