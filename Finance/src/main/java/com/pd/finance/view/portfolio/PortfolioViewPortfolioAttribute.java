package com.pd.finance.view.portfolio;

import com.pd.finance.view.IViewAttribute;

public enum PortfolioViewPortfolioAttribute implements IViewAttribute {
    Shares("Shares","Shares"),
    TradeDate("TradeDate","Trade Date"),
    HighLimit("HighLimit","High Limit"),
    LowLimit("LowLimit","Low Limit"),
    CostPerShare("CostPerShare","Cost Per Share"),
    MarketValue("MarketValue","Market Value"),
    TotalChange("TotalChange","Total Change"),
    TotalChangePercentage("TotalChangePercentage","Total Change Percentage"),
    DayChange("DayChange","Day Change"),
    DayChangePercentage("DayChangePercentage","Day Change Percentage"),
    Notes("Notes","Notes"),
    NoOfLots("NoOfLots","No Of Lots"),
    AnnualisedGains("AnnualisedGains","Annualised Gains");

    private final String name;
    private final String displayName;

    PortfolioViewPortfolioAttribute(String name, String displayName) {
        this.name=name;
        this.displayName = displayName;
    }
    PortfolioViewPortfolioAttribute(String name ) {
        this.name=name;
        this.displayName = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}
