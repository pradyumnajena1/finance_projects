package com.pd.finance.model;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;
@JsonIgnoreProperties(ignoreUnknown = true)
public class EquityStockExchangeDetails {

    @JsonProperty("exchange")
    private String exchange;
    @JsonProperty("shortname")
    private String shortname;
    @JsonProperty("quoteType")
    private String quoteType;
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("index")
    private String index;
    @JsonProperty("score")
    private Long score;
    @JsonProperty("typeDisp")
    private String typeDisp;
    @JsonProperty("longname")
    private String longname;
    @JsonProperty("isYahooFinance")
    private Boolean isYahooFinance;
    @JsonProperty("prevName")
    private String prevName;
    @JsonProperty("nameChangeDate")
    private String nameChangeDate;


    public EquityStockExchangeDetails() {
    }

    @JsonProperty("exchange")
    public String getExchange() {
        return exchange;
    }

    @JsonProperty("exchange")
    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    @JsonProperty("shortname")
    public String getShortname() {
        return shortname;
    }

    @JsonProperty("shortname")
    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    @JsonProperty("quoteType")
    public String getQuoteType() {
        return quoteType;
    }

    @JsonProperty("quoteType")
    public void setQuoteType(String quoteType) {
        this.quoteType = quoteType;
    }

    @JsonProperty("symbol")
    public String getSymbol() {
        return symbol;
    }

    @JsonProperty("symbol")
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @JsonProperty("index")
    public String getIndex() {
        return index;
    }

    @JsonProperty("index")
    public void setIndex(String index) {
        this.index = index;
    }

    @JsonProperty("score")
    public Long getScore() {
        return score;
    }

    @JsonProperty("score")
    public void setScore(Long score) {
        this.score = score;
    }

    @JsonProperty("typeDisp")
    public String getTypeDisp() {
        return typeDisp;
    }

    @JsonProperty("typeDisp")
    public void setTypeDisp(String typeDisp) {
        this.typeDisp = typeDisp;
    }

    @JsonProperty("longname")
    public String getLongname() {
        return longname;
    }

    @JsonProperty("longname")
    public void setLongname(String longname) {
        this.longname = longname;
    }

    @JsonProperty("isYahooFinance")
    public Boolean getIsYahooFinance() {
        return isYahooFinance;
    }

    @JsonProperty("isYahooFinance")
    public void setIsYahooFinance(Boolean isYahooFinance) {
        this.isYahooFinance = isYahooFinance;
    }

    @JsonProperty("prevName")
    public String getPrevName() {
        return prevName;
    }

    @JsonProperty("prevName")
    public void setPrevName(String prevName) {
        this.prevName = prevName;
    }

    @JsonProperty("nameChangeDate")
    public String getNameChangeDate() {
        return nameChangeDate;
    }

    @JsonProperty("nameChangeDate")
    public void setNameChangeDate(String nameChangeDate) {
        this.nameChangeDate = nameChangeDate;
    }

}
