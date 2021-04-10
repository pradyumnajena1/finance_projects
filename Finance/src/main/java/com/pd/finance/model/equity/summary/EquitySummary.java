package com.pd.finance.model.equity.summary;

import com.pd.finance.model.EquityAttribute;


public class EquitySummary extends EquityAttribute {

    private RecommendationTrend recommendationTrend;
    private Earnings earnings;
    private DefaultKeyStatistics defaultKeyStatistics;
    private SummaryProfile summaryProfile;
    private FinancialData financialData;

    public RecommendationTrend getRecommendationTrend() {
        return recommendationTrend;
    }

    public void setRecommendationTrend(RecommendationTrend recommendationTrend) {
        this.recommendationTrend = recommendationTrend;
    }

    public Earnings getEarnings() {
        return earnings;
    }

    public void setEarnings(Earnings earnings) {
        this.earnings = earnings;
    }

    public DefaultKeyStatistics getDefaultKeyStatistics() {
        return defaultKeyStatistics;
    }

    public void setDefaultKeyStatistics(DefaultKeyStatistics defaultKeyStatistics) {
        this.defaultKeyStatistics = defaultKeyStatistics;
    }

    public SummaryProfile getSummaryProfile() {
        return summaryProfile;
    }

    public void setSummaryProfile(SummaryProfile summaryProfile) {
        this.summaryProfile = summaryProfile;
    }

    public FinancialData getFinancialData() {
        return financialData;
    }

    public void setFinancialData(FinancialData financialData) {
        this.financialData = financialData;
    }
}
