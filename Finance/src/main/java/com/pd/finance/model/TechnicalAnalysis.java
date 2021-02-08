package com.pd.finance.model;

public class TechnicalAnalysis {

    private TechAnalysisSummary summary;
    private MovingAvgDetails movingAverages;
    private TechnicalIndicatorDetails technicalIndicator;
    private MovingAvgCrossoverDetails movingAveragesCrossovers;

    public TechnicalAnalysis() {
    }

    public TechAnalysisSummary getSummary() {
        return summary;
    }

    public void setSummary(TechAnalysisSummary summary) {
        this.summary = summary;
    }

    public MovingAvgDetails getMovingAverages() {
        return movingAverages;
    }

    public void setMovingAverages(MovingAvgDetails movingAverages) {
        this.movingAverages = movingAverages;
    }

    public TechnicalIndicatorDetails getTechnicalIndicator() {
        return technicalIndicator;
    }

    public void setTechnicalIndicator(TechnicalIndicatorDetails technicalIndicator) {
        this.technicalIndicator = technicalIndicator;
    }

    public MovingAvgCrossoverDetails getMovingAveragesCrossovers() {
        return movingAveragesCrossovers;
    }

    public void setMovingAveragesCrossovers(MovingAvgCrossoverDetails movingAveragesCrossovers) {
        this.movingAveragesCrossovers = movingAveragesCrossovers;
    }
}
