package com.pd.finance.model;

public class TechnicalAnalysis {

    private int movingAverages;
    private int technicalIndicator;
    private int movingAveragesCrossovers;

    public TechnicalAnalysis() {
    }

    public int getMovingAverages() {
        return movingAverages;
    }

    public void setMovingAverages(int movingAverages) {
        this.movingAverages = movingAverages;
    }

    public int getTechnicalIndicator() {
        return technicalIndicator;
    }

    public void setTechnicalIndicator(int technicalIndicator) {
        this.technicalIndicator = technicalIndicator;
    }

    public int getMovingAveragesCrossovers() {
        return movingAveragesCrossovers;
    }

    public void setMovingAveragesCrossovers(int movingAveragesCrossovers) {
        this.movingAveragesCrossovers = movingAveragesCrossovers;
    }
}
