package com.pd.finance.request;

public class SwotFilter {
    private int minStrengths =0 ;
    private int minOpportunities = 0;
    private int maxThreats = Integer.MAX_VALUE;
    private int maxWeaknesses = Integer.MAX_VALUE;

    public int getMinStrengths() {
        return minStrengths;
    }

    public void setMinStrengths(int minStrengths) {
        this.minStrengths = minStrengths;
    }

    public int getMinOpportunities() {
        return minOpportunities;
    }

    public void setMinOpportunities(int minOpportunities) {
        this.minOpportunities = minOpportunities;
    }

    public int getMaxThreats() {
        return maxThreats;
    }

    public void setMaxThreats(int maxThreats) {
        this.maxThreats = maxThreats;
    }

    public int getMaxWeaknesses() {
        return maxWeaknesses;
    }

    public void setMaxWeaknesses(int maxWeaknesses) {
        this.maxWeaknesses = maxWeaknesses;
    }
}
