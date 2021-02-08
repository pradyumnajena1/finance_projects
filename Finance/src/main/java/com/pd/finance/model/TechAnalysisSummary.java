package com.pd.finance.model;

public class TechAnalysisSummary {


    private TechAnalysisSummaryValue movingAverages;
    private TechAnalysisSummaryValue technicalIndicator;
    private TechAnalysisSummaryValue movingAveragesCrossOver;

    public TechAnalysisSummaryValue getMovingAverages() {
        return movingAverages;
    }

    public void setMovingAverages(TechAnalysisSummaryValue movingAverages) {
        this.movingAverages = movingAverages;
    }

    public TechAnalysisSummaryValue getTechnicalIndicator() {
        return technicalIndicator;
    }

    public void setTechnicalIndicator(TechAnalysisSummaryValue technicalIndicator) {
        this.technicalIndicator = technicalIndicator;
    }

    public TechAnalysisSummaryValue getMovingAveragesCrossOver() {
        return movingAveragesCrossOver;
    }

    public void setMovingAveragesCrossOver(TechAnalysisSummaryValue movingAveragesCrossOver) {
        this.movingAveragesCrossOver = movingAveragesCrossOver;
    }
}
