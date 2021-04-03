package com.pd.finance.service;

import java.time.Period;
import java.time.temporal.TemporalAmount;

public enum HistoricalDataInterval {
    OneMinute("1m", null),
    TwoMinutes("2m",null),
    FiveMinutes("5m",null) ,
    FifteenMinutes("15m",null),
    ThirtyMinutes("30m",null),
    SixtyMinutes("60m",null),
    NinetyMinutes("90m",null),
    OneHour("1h",null),
    OneDay("1d",Period.ofDays(1)) ,
    FiveDays("5d",Period.ofDays(5)) ,
    OneWeek("1wk",Period.ofDays(7)),
    OneMonth("1mo",Period.ofDays(30)),
    ThreeMonths("3mo",Period.ofDays(90));

    private final String intervalString;
    private final TemporalAmount ttl;

    HistoricalDataInterval(String s,TemporalAmount ttl) {
        this.intervalString = s;
        this.ttl = ttl;
    }

    public String getIntervalString() {
        return intervalString;
    }

    public   TemporalAmount getTtl(){return ttl;}
}
