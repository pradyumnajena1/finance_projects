package com.pd.finance.response.chart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "currency",
        "symbol",
        "exchangeName",
        "instrumentType",
        "firstTradeDate",
        "regularMarketTime",
        "gmtoffset",
        "timezone",
        "exchangeTimezoneName",
        "regularMarketPrice",
        "chartPreviousClose",
        "previousClose",
        "scale",
        "priceHint",
        "currentTradingPeriod",
        "tradingPeriods",
        "dataGranularity",
        "range",
        "validRanges"
})
public class Meta {
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("exchangeName")
    private String exchangeName;
    @JsonProperty("instrumentType")
    private String instrumentType;
    @JsonProperty("firstTradeDate")
    private Long firstTradeDate;
    @JsonProperty("regularMarketTime")
    private Long regularMarketTime;
    @JsonProperty("gmtoffset")
    private Long gmtoffset;
    @JsonProperty("timezone")
    private String timezone;
    @JsonProperty("exchangeTimezoneName")
    private String exchangeTimezoneName;
    @JsonProperty("regularMarketPrice")
    private Double regularMarketPrice;
    @JsonProperty("chartPreviousClose")
    private Double chartPreviousClose;
    @JsonProperty("previousClose")
    private Double previousClose;
    @JsonProperty("scale")
    private Long scale;
    @JsonProperty("priceHint")
    private Long priceHint;
    @JsonProperty("currentTradingPeriod")
    private CurrentTradingPeriod currentTradingPeriod;
    @JsonProperty("tradingPeriods")
    private List<List<TradingPeriod>> tradingPeriods = new ArrayList<List<TradingPeriod>>();
    @JsonProperty("dataGranularity")
    private String dataGranularity;
    @JsonProperty("range")
    private String range;
    @JsonProperty("validRanges")
    private List<String> validRanges = new ArrayList<String>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @JsonProperty("symbol")
    public String getSymbol() {
        return symbol;
    }

    @JsonProperty("symbol")
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @JsonProperty("exchangeName")
    public String getExchangeName() {
        return exchangeName;
    }

    @JsonProperty("exchangeName")
    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    @JsonProperty("instrumentType")
    public String getInstrumentType() {
        return instrumentType;
    }

    @JsonProperty("instrumentType")
    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }

    @JsonProperty("firstTradeDate")
    public Long getFirstTradeDate() {
        return firstTradeDate;
    }

    @JsonProperty("firstTradeDate")
    public void setFirstTradeDate(Long firstTradeDate) {
        this.firstTradeDate = firstTradeDate;
    }

    @JsonProperty("regularMarketTime")
    public Long getRegularMarketTime() {
        return regularMarketTime;
    }

    @JsonProperty("regularMarketTime")
    public void setRegularMarketTime(Long regularMarketTime) {
        this.regularMarketTime = regularMarketTime;
    }

    @JsonProperty("gmtoffset")
    public Long getGmtoffset() {
        return gmtoffset;
    }

    @JsonProperty("gmtoffset")
    public void setGmtoffset(Long gmtoffset) {
        this.gmtoffset = gmtoffset;
    }

    @JsonProperty("timezone")
    public String getTimezone() {
        return timezone;
    }

    @JsonProperty("timezone")
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    @JsonProperty("exchangeTimezoneName")
    public String getExchangeTimezoneName() {
        return exchangeTimezoneName;
    }

    @JsonProperty("exchangeTimezoneName")
    public void setExchangeTimezoneName(String exchangeTimezoneName) {
        this.exchangeTimezoneName = exchangeTimezoneName;
    }

    @JsonProperty("regularMarketPrice")
    public Double getRegularMarketPrice() {
        return regularMarketPrice;
    }

    @JsonProperty("regularMarketPrice")
    public void setRegularMarketPrice(Double regularMarketPrice) {
        this.regularMarketPrice = regularMarketPrice;
    }

    @JsonProperty("chartPreviousClose")
    public Double getChartPreviousClose() {
        return chartPreviousClose;
    }

    @JsonProperty("chartPreviousClose")
    public void setChartPreviousClose(Double chartPreviousClose) {
        this.chartPreviousClose = chartPreviousClose;
    }

    @JsonProperty("previousClose")
    public Double getPreviousClose() {
        return previousClose;
    }

    @JsonProperty("previousClose")
    public void setPreviousClose(Double previousClose) {
        this.previousClose = previousClose;
    }

    @JsonProperty("scale")
    public Long getScale() {
        return scale;
    }

    @JsonProperty("scale")
    public void setScale(Long scale) {
        this.scale = scale;
    }

    @JsonProperty("priceHint")
    public Long getPriceHint() {
        return priceHint;
    }

    @JsonProperty("priceHint")
    public void setPriceHint(Long priceHint) {
        this.priceHint = priceHint;
    }

    @JsonProperty("currentTradingPeriod")
    public CurrentTradingPeriod getCurrentTradingPeriod() {
        return currentTradingPeriod;
    }

    @JsonProperty("currentTradingPeriod")
    public void setCurrentTradingPeriod(CurrentTradingPeriod currentTradingPeriod) {
        this.currentTradingPeriod = currentTradingPeriod;
    }

    @JsonProperty("tradingPeriods")
    public List<List<TradingPeriod>> getTradingPeriods() {
        return tradingPeriods;
    }

    @JsonProperty("tradingPeriods")
    public void setTradingPeriods(List<List<TradingPeriod>> tradingPeriods) {
        this.tradingPeriods = tradingPeriods;
    }

    @JsonProperty("dataGranularity")
    public String getDataGranularity() {
        return dataGranularity;
    }

    @JsonProperty("dataGranularity")
    public void setDataGranularity(String dataGranularity) {
        this.dataGranularity = dataGranularity;
    }

    @JsonProperty("range")
    public String getRange() {
        return range;
    }

    @JsonProperty("range")
    public void setRange(String range) {
        this.range = range;
    }

    @JsonProperty("validRanges")
    public List<String> getValidRanges() {
        return validRanges;
    }

    @JsonProperty("validRanges")
    public void setValidRanges(List<String> validRanges) {
        this.validRanges = validRanges;
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
