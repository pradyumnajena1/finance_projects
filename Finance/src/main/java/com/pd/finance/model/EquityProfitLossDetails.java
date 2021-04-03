package com.pd.finance.model;

public class EquityProfitLossDetails extends EquityAttribute{

    private CompoundedSalesGrowthDetails salesGrowthDetails;
    private CompoundedProfitGrowthDetails profitGrowthDetails;
    private CompoundedStockPriceCagr stockPriceCagr;
    private CompoundedReturnOnEquity returnOnEquity;

    public CompoundedSalesGrowthDetails getSalesGrowthDetails() {
        return salesGrowthDetails;
    }

    public void setSalesGrowthDetails(CompoundedSalesGrowthDetails salesGrowthDetails) {
        this.salesGrowthDetails = salesGrowthDetails;
    }

    public CompoundedProfitGrowthDetails getProfitGrowthDetails() {
        return profitGrowthDetails;
    }

    public void setProfitGrowthDetails(CompoundedProfitGrowthDetails profitGrowthDetails) {
        this.profitGrowthDetails = profitGrowthDetails;
    }

    public CompoundedStockPriceCagr getStockPriceCagr() {
        return stockPriceCagr;
    }

    public void setStockPriceCagr(CompoundedStockPriceCagr stockPriceCagr) {
        this.stockPriceCagr = stockPriceCagr;
    }

    public CompoundedReturnOnEquity getReturnOnEquity() {
        return returnOnEquity;
    }

    public void setReturnOnEquity(CompoundedReturnOnEquity returnOnEquity) {
        this.returnOnEquity = returnOnEquity;
    }
}
