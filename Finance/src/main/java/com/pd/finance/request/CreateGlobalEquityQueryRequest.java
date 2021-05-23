package com.pd.finance.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateGlobalEquityQueryRequest {

    @JsonProperty("searchRequest")
    private EquitySearchRequest searchRequest;

    public EquitySearchRequest getSearchRequest() {
        return searchRequest;
    }

    public void setSearchRequest(EquitySearchRequest searchRequest) {
        this.searchRequest = searchRequest;
    }
}
