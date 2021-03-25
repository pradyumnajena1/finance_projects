package com.pd.finance.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Constants {
    public static final String EXCHANGE_NSI = "NSI";
    public static final String EXCHANGE_BSE = "BSE";

    public static final  Set<String> allowedExchanges = new HashSet<>(Arrays.asList(EXCHANGE_NSI, EXCHANGE_BSE));



    public static final String SOURCE_MONEY_CONTROL = "Money Control";
    public static final String SOURCE_YAHOO_FINANCE = "Yahoo Finance";
    public static final String SOURCE_SCREENER_IO = "Screener Io";
}
