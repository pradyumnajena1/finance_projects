package com.pd.finance.response;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EquityStockExchangeDetailsResponse {

    @JsonProperty("exchange")
    private String exchange;
    @JsonProperty("shortname")
    private String shortName;
    @JsonProperty("quoteType")
    private String quoteType;
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("index")
    private String index;
    @JsonProperty("score")
    private Long score;
    @JsonProperty("typeDisp")
    private String typeDisplay;
    @JsonProperty("longname")
    private String longName;
    @JsonProperty("isYahooFinance")
    private Boolean isYahooFinance;
    @JsonProperty("prevName")
    private String prevName;
    @JsonProperty("nameChangeDate")
    private String nameChangeDate;


    public EquityStockExchangeDetailsResponse() {
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getQuoteType() {
        return quoteType;
    }

    public void setQuoteType(String quoteType) {
        this.quoteType = quoteType;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public String getTypeDisplay() {
        return typeDisplay;
    }

    public void setTypeDisplay(String typeDisplay) {
        this.typeDisplay = typeDisplay;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public Boolean getYahooFinance() {
        return isYahooFinance;
    }

    public void setYahooFinance(Boolean yahooFinance) {
        isYahooFinance = yahooFinance;
    }

    public String getPrevName() {
        return prevName;
    }

    public void setPrevName(String prevName) {
        this.prevName = prevName;
    }

    public String getNameChangeDate() {
        return nameChangeDate;
    }

    public void setNameChangeDate(String nameChangeDate) {
        this.nameChangeDate = nameChangeDate;
    }
}
