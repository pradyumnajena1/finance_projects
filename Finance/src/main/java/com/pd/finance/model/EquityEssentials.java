package com.pd.finance.model;

import java.util.Map;

public class EquityEssentials {

    private String essentials;

    private Map<String ,Boolean> financial;
    private Map<String ,Boolean> ownerships;
    private Map<String ,Boolean> industryComparison;
    private Map<String ,Boolean> others;

    public String getEssentials() {
        return essentials;
    }

    public void setEssentials(String essentials) {
        this.essentials = essentials;
    }

    public Map<String, Boolean> getFinancial() {
        return financial;
    }

    public void setFinancial(Map<String, Boolean> financial) {
        this.financial = financial;
    }

    public Map<String, Boolean> getOwnerships() {
        return ownerships;
    }

    public void setOwnerships(Map<String, Boolean> ownerships) {
        this.ownerships = ownerships;
    }

    public Map<String, Boolean> getIndustryComparison() {
        return industryComparison;
    }

    public void setIndustryComparison(Map<String, Boolean> industryComparison) {
        this.industryComparison = industryComparison;
    }

    public Map<String, Boolean> getOthers() {
        return others;
    }

    public void setOthers(Map<String, Boolean> others) {
        this.others = others;
    }
}
