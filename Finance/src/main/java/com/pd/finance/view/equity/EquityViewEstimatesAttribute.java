package com.pd.finance.view.equity;

public enum EquityViewEstimatesAttribute {
    OneYearTargetEstimate("OneYearTargetEstimate","1y target Est"),
    EpsEstimateNextYear("EpsEstimateNextYear","EPS est. next year"),
    EpsCurrentYear("EpsCurrentYear","EPS current year"),
    PegRatioFiveYearsExpected("PegRatioFiveYearsExpected","PEG ratio(5-yr expected)");

    private final String name;
    private final String displayName;

    EquityViewEstimatesAttribute(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }
    EquityViewEstimatesAttribute(String name ) {
        this.name = name;
        this.displayName = name;
    }
}
