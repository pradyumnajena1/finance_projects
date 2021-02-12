package com.pd.finance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mysema.query.annotations.QueryEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@QueryEntity
@Document
public class Equity {
    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(Equity.class);


    @Id
    private String id;

    private String name;
    private String nseId;
    private String bseId;
    private String url;
    private String sector;
    private String exchange;
    private EquityPerformances performances;
    private EquitySwotDetails swotDetails;
    private TechnicalDetails technicalDetails;
    private EquityEssentials essentials;
    private EquityInsights insights;
    private EquityOverview overview;
    private EquityCurrentPriceStats equityCurrentPriceStats  ;
    private List<EquityStockExchangeDetails> stockExchangeDetails;

    public List<EquityStockExchangeDetails> getStockExchangeDetails() {
        return stockExchangeDetails;
    }

    public void setStockExchangeDetails(List<EquityStockExchangeDetails> stockExchangeDetails) {
        this.stockExchangeDetails = stockExchangeDetails;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EquityInsights getInsights() {
        return insights;
    }

    public void setInsights(EquityInsights insights) {
        this.insights = insights;
    }

    public String getNseId() {
        return nseId;
    }

    public void setNseId(String nseId) {
        this.nseId = nseId;
    }

    public String getBseId() {
        return bseId;
    }

    public void setBseId(String bseId) {
        this.bseId = bseId;
    }

    public EquityOverview getOverview() {
        return overview;
    }

    public void setOverview(EquityOverview overview) {
        this.overview = overview;
    }

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


    public EquityCurrentPriceStats getEquityCurrentPriceStats() {
        return equityCurrentPriceStats;
    }

    public void setEquityCurrentPriceStats(EquityCurrentPriceStats equityCurrentPriceStats) {
        this.equityCurrentPriceStats = equityCurrentPriceStats;
    }

    public EquityPerformances getPerformances() {
        return performances;
    }

    public void setPerformances(EquityPerformances performances) {
        this.performances = performances;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equity equity = (Equity) o;
        return name.equals(equity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
