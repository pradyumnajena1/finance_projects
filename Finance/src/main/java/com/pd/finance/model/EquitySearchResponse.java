package com.pd.finance.model;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.*;
import com.pd.finance.response.EquityStockExchangeDetailsResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EquitySearchResponse {

    @JsonProperty("count")
    private Long count;
    @JsonProperty("quotes")
    private List<EquityStockExchangeDetailsResponse> stockExchangeDetails = new ArrayList<EquityStockExchangeDetailsResponse>();


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
    public List<EquityStockExchangeDetailsResponse> getStockExchangeDetails() {
        return stockExchangeDetails;
    }

    @JsonProperty("quotes")
    public void setStockExchangeDetails(List<EquityStockExchangeDetailsResponse> quotes) {
        this.stockExchangeDetails = quotes;
    }

}
