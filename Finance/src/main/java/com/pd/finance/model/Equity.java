package com.pd.finance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pd.finance.service.MarketService;
import org.jsoup.nodes.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.stream.Collectors;

public class Equity {
    @JsonIgnore
    private static Logger logger = LoggerFactory.getLogger(Equity.class);


    private String name;
    private String url;
    private String sector;
    private String exchange;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal lastPrice;
    private BigDecimal prevClose;
    private BigDecimal change;
    private BigDecimal percentageGain;
    private EquityPerformances performances;
    private EquitySwotDetails swotDetails;
    private TechnicalDetails technicalDetails;
    private EquityEssentials essentials;

    public EquityEssentials getEssentials() {
        return essentials;
    }

    public void setEssentials(EquityEssentials essentials) {
        this.essentials = essentials;
    }

    public TechnicalDetails getTechnicalDetails() {
        return technicalDetails;
    }

    public void setTechnicalDetails(TechnicalDetails technicalDetails) {
        this.technicalDetails = technicalDetails;
    }

    public EquitySwotDetails getSwotDetails() {
        return swotDetails;
    }

    public void setSwotDetails(EquitySwotDetails swotDetails) {
        this.swotDetails = swotDetails;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(BigDecimal lastPrice) {
        this.lastPrice = lastPrice;
    }

    public BigDecimal getPrevClose() {
        return prevClose;
    }

    public void setPrevClose(BigDecimal prevClose) {
        this.prevClose = prevClose;
    }

    public BigDecimal getChange() {
        return change;
    }

    public void setChange(BigDecimal change) {
        this.change = change;
    }

    public BigDecimal getPercentageGain() {
        return percentageGain;
    }

    public void setPercentageGain(BigDecimal percentageGain) {
        this.percentageGain = percentageGain;
    }

    public EquityPerformances getPerformances() {
        return performances;
    }

    public void setPerformances(EquityPerformances performances) {
        this.performances = performances;
    }
}
