package com.pd.finance.model;

import java.util.ArrayList;
import java.util.List;

public class EquitySwotDetails {


    private List<String> strengths = new ArrayList<>();
    private List<String> weaknesses= new ArrayList<>();
    private List<String> opportunities= new ArrayList<>();
    private List<String> threats= new ArrayList<>();

    public EquitySwotDetails(){

    }


    public List<String> getStrengths() {
        return strengths;
    }

    public void setStrengths(List<String> strengths) {
        this.strengths = strengths;
    }

    public List<String> getWeaknesses() {
        return weaknesses;
    }

    public void setWeaknesses(List<String> weaknesses) {
        this.weaknesses = weaknesses;
    }

    public List<String> getOpportunities() {
        return opportunities;
    }

    public void setOpportunities(List<String> opportunities) {
        this.opportunities = opportunities;
    }

    public List<String> getThreats() {
        return threats;
    }

    public void setThreats(List<String> threats) {
        this.threats = threats;
    }
}
