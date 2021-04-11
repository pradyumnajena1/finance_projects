package com.pd.finance.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BulkCreateEquityRequest {

    @JsonProperty("equities")
    private List<CreateEquityRequest> equities;

    public List<CreateEquityRequest> getEquities() {
        return equities;
    }

    public void setEquities(List<CreateEquityRequest> equities) {
        this.equities = equities;
    }
}
