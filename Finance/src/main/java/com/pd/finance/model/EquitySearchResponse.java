package com.pd.finance.model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EquitySearchResponse {

    @JsonProperty("count")
    private Long count;
    @JsonProperty("quotes")
    private List<EquityStockExchangeDetails> stockExchangeDetails = new ArrayList<EquityStockExchangeDetails>();


    public EquitySearchResponse() {
    }

    @JsonProperty("count")
    public Long getCount() {
        return count;
    }

    @JsonProperty("count")
    public void setCount(Long count) {
        this.count = count;
    }

    @JsonProperty("quotes")
    public List<EquityStockExchangeDetails> getStockExchangeDetails() {
        return stockExchangeDetails;
    }

    @JsonProperty("quotes")
    public void setStockExchangeDetails(List<EquityStockExchangeDetails> quotes) {
        this.stockExchangeDetails = quotes;
    }

}
