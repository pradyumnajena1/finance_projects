package com.pd.finance.htmlscrapper.equity;

public class TechnicalAnalysisPeriodCssSelectors {
    private final String period;
    private final String summaryCssQuery;
    private final String movingAvgCssQuery;
    private final String movingAvgCrossoverCssQuery;
    private final String technicalIndicatorCssQuery;

    public TechnicalAnalysisPeriodCssSelectors(String period, String summaryCssQuery, String movingAvgCssQuery, String movingAvgCrossoverCssQuery, String technicalIndicatorCssQuery) {
        this.period = period;
        this.summaryCssQuery = summaryCssQuery;
        this.movingAvgCssQuery = movingAvgCssQuery;
        this.movingAvgCrossoverCssQuery = movingAvgCrossoverCssQuery;
        this.technicalIndicatorCssQuery = technicalIndicatorCssQuery;
    }

    public String getPeriod() {
        return period;
    }

    public String getSummaryCssQuery() {
        return summaryCssQuery;
    }

    public String getMovingAvgCssQuery() {
        return movingAvgCssQuery;
    }

    public String getMovingAvgCrossoverCssQuery() {
        return movingAvgCrossoverCssQuery;
    }

    public String getTechnicalIndicatorCssQuery() {
        return technicalIndicatorCssQuery;
    }
}
