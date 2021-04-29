package com.pd.finance.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.pd.finance.filter.IFilter;
import com.pd.finance.filter.code.*;
import com.pd.finance.filter.db.*;
import com.pd.finance.model.Equity;
import com.pd.finance.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EquitySearchRequest {
    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(EquitySearchRequest.class);

    @JsonProperty("recentGainersFilter")
    private RecentGainersFilter  recentGainersFilter;

    @JsonProperty("exchangeFilter")
    private EquityExchangeFilter exchangeFilter;

    @JsonProperty("namesFilter")
    private EquityNamesFilter namesFilter;

    @JsonProperty("performanceFilter")
    private PerformanceFilter performanceFilter;

    @JsonProperty("swotFilter")
    private SwotFilter swotFilter;

    @JsonProperty("overviewFilter")
    private OverviewFilter overviewFilter;

    @JsonProperty("technicalPeriodFilter")
    private TechnicalPeriodFilter technicalPeriodFilter;

    @JsonProperty("insightFilter")
    private EquityInsightFilter insightFilter;

    @JsonProperty("profitLossFilter")
    private ProfitLossFilter profitLossFilter;

    @JsonProperty("brokerResearchFilter")
    private BrokerResearchFilter brokerResearchFilter;

    @JsonProperty("recommendationTrendFilter")
    private RecommendationTrendFilter recommendationTrendFilter;

    @JsonProperty("prosAndConsFilter")
    private ProsAndConsFilter prosAndConsFilter;

    public ProsAndConsFilter getProsAndConsFilter() {
        return prosAndConsFilter;
    }

    public void setProsAndConsFilter(ProsAndConsFilter prosAndConsFilter) {
        this.prosAndConsFilter = prosAndConsFilter;
    }

    public RecommendationTrendFilter getRecommendationTrendFilter() {
        return recommendationTrendFilter;
    }

    public void setRecommendationTrendFilter(RecommendationTrendFilter recommendationTrendFilter) {
        this.recommendationTrendFilter = recommendationTrendFilter;
    }

    public BrokerResearchFilter getBrokerResearchFilter() {
        return brokerResearchFilter;
    }

    public void setBrokerResearchFilter(BrokerResearchFilter brokerResearchFilter) {
        this.brokerResearchFilter = brokerResearchFilter;
    }

    public RecentGainersFilter getRecentGainersFilter() {
        return recentGainersFilter;
    }

    public void setRecentGainersFilter(RecentGainersFilter recentGainersFilter) {
        this.recentGainersFilter = recentGainersFilter;
    }

    public EquityInsightFilter getInsightFilter() {
        return insightFilter;
    }

    public void setInsightFilter(EquityInsightFilter insightFilter) {
        this.insightFilter = insightFilter;
    }

    public EquityExchangeFilter getExchangeFilter() {
        return exchangeFilter;
    }

    public void setExchangeFilter(EquityExchangeFilter exchangeFilter) {
        this.exchangeFilter = exchangeFilter;
    }

    public EquityNamesFilter getNamesFilter() {
        return namesFilter;
    }

    public void setNamesFilter(EquityNamesFilter namesFilter) {
        this.namesFilter = namesFilter;
    }

    public OverviewFilter getOverviewFilter() {
        return overviewFilter;
    }

    public void setOverviewFilter(OverviewFilter overviewFilter) {
        this.overviewFilter = overviewFilter;
    }

    public SwotFilter getSwotFilter() {
        return swotFilter;
    }

    public void setSwotFilter(SwotFilter swotFilter) {
        this.swotFilter = swotFilter;
    }

    public PerformanceFilter getPerformanceFilter() {
        return performanceFilter;
    }

    public void setPerformanceFilter(PerformanceFilter performanceFilter) {
        this.performanceFilter = performanceFilter;
    }

    public TechnicalPeriodFilter getTechnicalPeriodFilter() {
        return technicalPeriodFilter;
    }

    public void setTechnicalPeriodFilter(TechnicalPeriodFilter technicalPeriodFilter) {
        this.technicalPeriodFilter = technicalPeriodFilter;
    }

    public ProfitLossFilter getProfitLossFilter() {
        return profitLossFilter;
    }

    public void setProfitLossFilter(ProfitLossFilter profitLossFilter) {
        this.profitLossFilter = profitLossFilter;
    }

    public List<FieldError> validate(){
        List<FieldError> errors = new ArrayList<>();
        if(technicalPeriodFilter !=null){
            errors.addAll(technicalPeriodFilter.validate());
        }
        return  errors;
    }

    @Override
    public String toString() {
        String jsonString = null;
        try {
            jsonString = JsonUtils.serialize(this);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
        return "EquitySearchRequest : "+ jsonString;
    }

    @JsonIgnore
    public List<IFilter<Equity>> getFilters( ) {
        List<IFilter<Equity>> filters = new ArrayList<>();

        CollectFilters(filters,  getExchangeFilter());
        CollectFilters(filters,  getNamesFilter());
        CollectFilters(filters,  getOverviewFilter());
        CollectFilters(filters,  getPerformanceFilter());
        CollectFilters(filters,  getSwotFilter());
        CollectFilters(filters,  getTechnicalPeriodFilter());
        CollectFilters(filters,  getInsightFilter());
        CollectFilters(filters,  getProfitLossFilter());
        CollectFilters(filters,  getRecentGainersFilter());
        CollectFilters(filters,  getBrokerResearchFilter());
        CollectFilters(filters,  getRecommendationTrendFilter());
        CollectFilters(filters,  getProsAndConsFilter());

        return filters;
    }

    private void CollectFilters(List<IFilter<Equity>> filters, IFilter<Equity> filter) {
        if(filter!=null){
            filters.add(filter);
        }
    }
}
