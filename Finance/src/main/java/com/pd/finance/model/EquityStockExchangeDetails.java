package com.pd.finance.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mysema.query.annotations.QueryEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@QueryEntity
@Document("stock_exchange")
public class EquityStockExchangeDetails {
    @Id
    private String id;

     @Field("exchange")
    private String exchange;

    @Field("shortName")
    private String shortName;

    @Field("quoteType")
    private String quoteType;

    @Field("symbol")
    private String symbol;

    @Field("index")
    private String index;

    @Field("score")
    private Long score;

    @Field("typeDisplay")
    private String typeDisplay;

    @Field("longName")
    private String longName;

    @Field("isYahooFinance")
    private Boolean isYahooFinance;

    @Field("previousName")
    private String previousName;

    @Field("nameChangeDate")
    private String nameChangeDate;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPreviousName() {
        return previousName;
    }

    public void setPreviousName(String previousName) {
        this.previousName = previousName;
    }

    public String getNameChangeDate() {
        return nameChangeDate;
    }

    public void setNameChangeDate(String nameChangeDate) {
        this.nameChangeDate = nameChangeDate;
    }
}
