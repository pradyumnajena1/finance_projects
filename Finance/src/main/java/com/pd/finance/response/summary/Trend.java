
package com.pd.finance.response.summary;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "period",
    "strongBuy",
    "buy",
    "hold",
    "sell",
    "strongSell"
})
@Generated("jsonschema2pojo")
public class Trend {

    @JsonProperty("period")
    private String period;
    @JsonProperty("strongBuy")
    private Long strongBuy;
    @JsonProperty("buy")
    private Long buy;
    @JsonProperty("hold")
    private Long hold;
    @JsonProperty("sell")
    private Long sell;
    @JsonProperty("strongSell")
    private Long strongSell;


    @JsonProperty("period")
    public String getPeriod() {
        return period;
    }

    @JsonProperty("period")
    public void setPeriod(String period) {
        this.period = period;
    }

    @JsonProperty("strongBuy")
    public Long getStrongBuy() {
        return strongBuy;
    }

    @JsonProperty("strongBuy")
    public void setStrongBuy(Long strongBuy) {
        this.strongBuy = strongBuy;
    }

    @JsonProperty("buy")
    public Long getBuy() {
        return buy;
    }

    @JsonProperty("buy")
    public void setBuy(Long buy) {
        this.buy = buy;
    }

    @JsonProperty("hold")
    public Long getHold() {
        return hold;
    }

    @JsonProperty("hold")
    public void setHold(Long hold) {
        this.hold = hold;
    }

    @JsonProperty("sell")
    public Long getSell() {
        return sell;
    }

    @JsonProperty("sell")
    public void setSell(Long sell) {
        this.sell = sell;
    }

    @JsonProperty("strongSell")
    public Long getStrongSell() {
        return strongSell;
    }

    @JsonProperty("strongSell")
    public void setStrongSell(Long strongSell) {
        this.strongSell = strongSell;
    }


}
