package com.pd.finance.model;

import java.util.List;

public class EquityPerformances {

    private final String type;
    private final List<EquityPerformance> performances;

    public EquityPerformances(String type, List<EquityPerformance> performances) {

        this.type = type;
        this.performances = performances;
    }

    public String getType() {
        return type;
    }

    public List<EquityPerformance> getPerformances() {
        return performances;
    }
}
