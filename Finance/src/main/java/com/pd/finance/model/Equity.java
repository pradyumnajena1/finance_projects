package com.pd.finance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mysema.query.annotations.QueryEntity;

import com.pd.finance.response.EquityStockExchangeDetailsResponse;
import com.pd.finance.utils.Constants;
import org.apache.cxf.common.util.ReflectionUtil;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@QueryEntity
@Document
public class Equity {
    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(Equity.class);


    @Id
    private String id;

    private EquityStockExchangeDetailsResponse stockExchangeDetails;

    private EquityIdentifiers equityIdentifiers = new EquityIdentifiers();

    private EquitySourceDetails sourceDetails = new EquitySourceDetails();


    private String sector;



    private EquityPerformances performances;
    private EquitySwotDetails swotDetails;
    private TechnicalDetails technicalDetails;
    private EquityEssentials essentials;
    private EquityInsights insights;
    private EquityOverview overview;
    private EquityCurrentPriceStats equityCurrentPriceStats;




    public EquityStockExchangeDetailsResponse getStockExchangeDetails() {
        return stockExchangeDetails;
    }

    public void setStockExchangeDetails(EquityStockExchangeDetailsResponse stockExchangeDetails) {
        this.stockExchangeDetails = stockExchangeDetails;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EquityIdentifiers getEquityIdentifiers() {
        return equityIdentifiers;
    }

    public void setEquityIdentifiers(EquityIdentifiers equityIdentifiers) {
        this.equityIdentifiers = equityIdentifiers;
    }

    public EquityInsights getInsights() {
        return insights;
    }

    public void setInsights(EquityInsights insights) {
        this.insights = insights;
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



    public EquitySourceDetails getSourceDetails() {
        return sourceDetails;
    }

    public void setSourceDetails(EquitySourceDetails sourceDetails) {
        this.sourceDetails = sourceDetails;
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
        return equityIdentifiers.equals(equity.equityIdentifiers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(equityIdentifiers);
    }
}
