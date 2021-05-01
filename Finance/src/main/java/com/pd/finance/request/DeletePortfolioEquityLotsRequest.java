package com.pd.finance.request;

import org.apache.commons.collections.CollectionUtils;

import java.util.Set;

public class DeletePortfolioEquityLotsRequest extends AbstractWebRequest{

    private Set<String> lotIds;

    public Set<String> getLotIds() {
        return lotIds;
    }

    public void setLotIds(Set<String> lotIds) {
        this.lotIds = lotIds;
    }

    @Override
    public void validate() {
        if(CollectionUtils.isEmpty(lotIds)){
            validationErrors.add("lotIds cant be empty");
        }
    }
}
