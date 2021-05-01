package com.pd.finance.request;

import org.apache.commons.collections.CollectionUtils;

import java.util.Set;

public class DeletePortfolioEquityRequest  extends AbstractWebRequest{
    private Set<String> equityIds;

    public Set<String> getEquityIds() {
        return equityIds;
    }

    public void setEquityIds(Set<String> equityIds) {
        this.equityIds = equityIds;
    }

    @Override
    public void validate() {
        if(CollectionUtils.isEmpty(equityIds)){
            validationErrors.add("equityIds cant be empty");
        }
    }
}
