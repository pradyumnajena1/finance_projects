package com.pd.finance.response.summary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "quoteSummary"
})
public class EquitySummaryResponse {
    @JsonProperty("quoteSummary")
    private EquitySummaries equitySummaries;

    public EquitySummaries getEquitySummaries() {
        return equitySummaries;
    }

    public void setEquitySummaries(EquitySummaries equitySummaries) {
        this.equitySummaries = equitySummaries;
    }

    public EquitySummary getEquitySummary(){
        EquitySummary equitySummary = null;
        EquitySummaries equitySummaries = getEquitySummaries();
        if(equitySummaries!=null){
            List<EquitySummary> summaries = equitySummaries.getSummaries();
            if(CollectionUtils.isNotEmpty(summaries)){
                equitySummary = summaries.get(0);
            }

        }

        return equitySummary;
    }
}
