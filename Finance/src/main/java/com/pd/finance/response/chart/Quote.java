package com.pd.finance.response.chart;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        "low",
        "open",
        "close",
        "high",
        "volume"
})
public class Quote {


    @JsonProperty("low")
    private List<Double> low = new ArrayList<Double>();
    @JsonProperty("open")
    private List<Double> open = new ArrayList<Double>();
    @JsonProperty("close")
    private List<Double> close = new ArrayList<Double>();
    @JsonProperty("high")
    private List<Double> high = new ArrayList<Double>();
    @JsonProperty("volume")
    private List<Long> volume = new ArrayList<Long>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("low")
    public List<Double> getLow() {
        return low;
    }

    @JsonProperty("low")
    public void setLow(List<Double> low) {
        this.low = low;
    }

    @JsonProperty("open")
    public List<Double> getOpen() {
        return open;
    }

    @JsonProperty("open")
    public void setOpen(List<Double> open) {
        this.open = open;
    }

    @JsonProperty("close")
    public List<Double> getClose() {
        return close;
    }

    @JsonProperty("close")
    public void setClose(List<Double> close) {
        this.close = close;
    }

    @JsonProperty("high")
    public List<Double> getHigh() {
        return high;
    }

    @JsonProperty("high")
    public void setHigh(List<Double> high) {
        this.high = high;
    }

    @JsonProperty("volume")
    public List<Long> getVolume() {
        return volume;
    }

    @JsonProperty("volume")
    public void setVolume(List<Long> volume) {
        this.volume = volume;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
