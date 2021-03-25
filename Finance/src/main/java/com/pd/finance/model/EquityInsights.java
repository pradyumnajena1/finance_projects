package com.pd.finance.model;

public class EquityInsights extends EquityAttribute{


    private EquityInsightLineItem insightHeadline;
    private PriceInsights priceInsights;
    private FinancialInsights financialInsights;
    private IndustryComparisionInsights industryComparisionInsights;
    private ShareholdingPatternInsights shareholdingPatternInsights;

    public EquityInsightLineItem getInsightHeadline() {
        return insightHeadline;
    }

    public void setInsightHeadline(EquityInsightLineItem insightHeadline) {
        this.insightHeadline = insightHeadline;
    }

    public PriceInsights getPriceInsights() {
        return priceInsights;
    }

    public void setPriceInsights(PriceInsights priceInsights) {
        this.priceInsights = priceInsights;
    }

    public FinancialInsights getFinancialInsights() {
        return financialInsights;
    }

    public void setFinancialInsights(FinancialInsights financialInsights) {
        this.financialInsights = financialInsights;
    }

    public IndustryComparisionInsights getIndustryComparisionInsights() {
        return industryComparisionInsights;
    }

    public void setIndustryComparisionInsights(IndustryComparisionInsights industryComparisionInsights) {
        this.industryComparisionInsights = industryComparisionInsights;
    }

    public ShareholdingPatternInsights getShareholdingPatternInsights() {
        return shareholdingPatternInsights;
    }

    public void setShareholdingPatternInsights(ShareholdingPatternInsights shareholdingPatternInsights) {
        this.shareholdingPatternInsights = shareholdingPatternInsights;
    }
}
