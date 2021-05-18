package com.pd.finance.view.equity;

public enum EquityViewBasicAttribute {
    Symbol("Symbol"),
    Company("Company"),
    Currency("Currency"),
    Exchange("Exchange"),
    LastPrice("LastPrice","Last Price"),
    Change("Change"),
    ChangePercent("ChangePercent","Change %"),
    MarketTime("MarketTime","Market Time"),
    Volume("Volume"),
    AverageVolume3M("AverageVolume3M","Average Volume 3 Months"),
    AverageVolume10D("AverageVolume10D","Average Volume 10 Days"),
    DayRange("DayRange","Day Range"),
    DayChart("DayChart","Day Chart");

    private final String name;
    private final String displayName;
    EquityViewBasicAttribute(String name) {
        this.name = name;
        this.displayName=name;
    }
    EquityViewBasicAttribute(String name, String displayName) {
        this.name = name;
        this.displayName=displayName;
    }
}
