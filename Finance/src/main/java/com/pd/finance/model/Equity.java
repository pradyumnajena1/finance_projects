package com.pd.finance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mysema.query.annotations.QueryEntity;

import com.pd.finance.model.equity.summary.EquitySummary;
import com.pd.finance.response.EquityStockExchangeDetailsResponse;
import com.pd.finance.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@QueryEntity
@Document
public class Equity extends EquityAttribute{
    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(Equity.class);


    @Id
    private String id;

    private EquityStockExchangeDetailsResponse stockExchangeDetails;

    private EquityIdentifiers equityIdentifiers = new EquityIdentifiers();

    private EquitySourceDetails sourceDetails = new EquitySourceDetails();


    private String sector;
    private EquityHistoricalData historicalData;
    private EquitySummary equitySummary;
    private BrokerResearchDetails brokerResearchDetails;


    private EquityPerformances performances;
    private EquitySwotDetails swotDetails;
    private TechnicalDetails technicalDetails;
    private EquityEssentials essentials;
    private EquityInsights insights;
    private EquityOverview overview;
    private EquityCurrentPriceStats equityCurrentPriceStats;


    private EquityProfitLossDetails profitLossDetails;
    private EquityProsAndConsDetails prosAndConsDetails;

    private EquityDealsDetails equityDealsDetails;
    private InsiderTransactionDetails insiderTransactionDetails;
    private ShareholdingDetails shareholdingDetails;
    private FundamentalRatios fundamentalRatios;

    public ShareholdingDetails getShareholdingDetails() {
        return shareholdingDetails;
    }

    public void setShareholdingDetails(ShareholdingDetails shareholdingDetails) {
        this.shareholdingDetails = shareholdingDetails;
    }

    public InsiderTransactionDetails getInsiderTransactionDetails() {
        return insiderTransactionDetails;
    }

    public void setInsiderTransactionDetails(InsiderTransactionDetails insiderTransactionDetails) {
        this.insiderTransactionDetails = insiderTransactionDetails;
    }

    public EquityDealsDetails getEquityDealsDetails() {
        return equityDealsDetails;
    }

    public void setEquityDealsDetails(EquityDealsDetails equityDealsDetails) {
        this.equityDealsDetails = equityDealsDetails;
    }

    public EquityProsAndConsDetails getProsAndConsDetails() {
        return prosAndConsDetails;
    }

    public void setProsAndConsDetails(EquityProsAndConsDetails prosAndConsDetails) {
        this.prosAndConsDetails = prosAndConsDetails;
    }

    public EquityProfitLossDetails getProfitLossDetails() {
        return profitLossDetails;
    }

    public void setProfitLossDetails(EquityProfitLossDetails profitLossDetails) {
        this.profitLossDetails = profitLossDetails;
    }

    public EquityStockExchangeDetailsResponse getStockExchangeDetails() {
        return stockExchangeDetails;
    }

    public void setStockExchangeDetails(EquityStockExchangeDetailsResponse stockExchangeDetails) {
        this.stockExchangeDetails = stockExchangeDetails;
    }

    public BrokerResearchDetails getBrokerResearchDetails() {
        return brokerResearchDetails;
    }

    public void setBrokerResearchDetails(BrokerResearchDetails brokerResearchDetails) {
        this.brokerResearchDetails = brokerResearchDetails;
    }

    public EquitySummary getEquitySummary() {
        return equitySummary;
    }

    public void setEquitySummary(EquitySummary equitySummary) {
        this.equitySummary = equitySummary;
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


    public EquityIdentifier getEquityIdentifier(String source){
      return   getEquityIdentifiers().getEquityIdentifier(source);
    }

    public EquityIdentifier getDefaultEquityIdentifier( ){
        return   getEquityIdentifier(Constants.SOURCE_MONEY_CONTROL);
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

    public EquityHistoricalData getHistoricalData() {
        return historicalData;
    }

    public void setHistoricalData(EquityHistoricalData historicalData) {
        this.historicalData = historicalData;
    }

    public EquitySourceDetails getSourceDetails() {
        return sourceDetails;
    }

    public SourceDetails getSourceDetails(String source){
        return getSourceDetails().getSourceDetails(source);
    }

    public SourceDetails getDefaultSourceDetails( ){
        return getSourceDetails(Constants.SOURCE_MONEY_CONTROL);
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

    public void setFundamentalRatios(FundamentalRatios fundamentalRatios) {
        this.fundamentalRatios = fundamentalRatios;
    }

    public FundamentalRatios getFundamentalRatios() {
        return fundamentalRatios;
    }
}
