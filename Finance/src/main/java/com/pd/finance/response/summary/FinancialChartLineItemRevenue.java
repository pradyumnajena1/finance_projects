
package com.pd.finance.response.summary;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "raw"
})
@Generated("jsonschema2pojo")
public class FinancialChartLineItemRevenue {

    @JsonProperty("raw")
    private Long raw;

    @JsonProperty("raw")
    public Long getRaw() {
        return raw;
    }

    @JsonProperty("raw")
    public void setRaw(Long raw) {
        this.raw = raw;
    }



}
