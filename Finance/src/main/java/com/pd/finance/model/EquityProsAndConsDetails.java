package com.pd.finance.model;

import java.util.List;

public class EquityProsAndConsDetails   extends EquityAttribute{
    private List<String> pros;
    private List<String> cons;

    public List<String> getPros() {
        return pros;
    }

    public void setPros(List<String> pros) {
        this.pros = pros;
    }

    public List<String> getCons() {
        return cons;
    }

    public void setCons(List<String> cons) {
        this.cons = cons;
    }
}
