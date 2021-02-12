package com.pd.finance.model;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EquitySearchResponse {

    @JsonProperty("quotes")
    private List<EquityStockExchangeDetails> stockExchangeDetails;

    public EquitySearchResponse(List<EquityStockExchangeDetails> stockExchangeDetails) {
        this.stockExchangeDetails = stockExchangeDetails;
    }

    public List<EquityStockExchangeDetails> getStockExchangeDetails() {
        return stockExchangeDetails;
    }

    public void setStockExchangeDetails(List<EquityStockExchangeDetails> stockExchangeDetails) {
        this.stockExchangeDetails = stockExchangeDetails;
    }
}
