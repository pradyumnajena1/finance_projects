package com.pd.finance.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EquitySearchByNameRequest extends AbstractWebRequest{
    @JsonProperty("searchTerms")
    private List<String> searchTerms;

    public List<String> getSearchTerms() {
        return searchTerms;
    }

    public void setSearchTerms(List<String> searchTerms) {
        this.searchTerms = searchTerms;
    }

    @Override
    public void validate() {
        if(CollectionUtils.isEmpty(searchTerms)){
            validationErrors.add("Search Terms must not be null or empty");
        }
    }

    @Override
    public String toString() {
        return "EquitySearchByNameRequest{" +
                "searchTerms=" + searchTerms +
                '}';
    }
}
