package com.pd.finance.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlockDealDetails {


    @JsonProperty("purchaseDeals")
    private List<EquityDeal> purchaseDeals;

    @JsonProperty("saleDeals")
    private List<EquityDeal> saleDeals;

    public List<EquityDeal> getPurchaseDeals() {
        return purchaseDeals;
    }

    public void setPurchaseDeals(List<EquityDeal> purchaseDeals) {
        this.purchaseDeals = purchaseDeals;
    }

    public List<EquityDeal> getSaleDeals() {
        return saleDeals;
    }

    public void setSaleDeals(List<EquityDeal> saleDeals) {
        this.saleDeals = saleDeals;
    }


}
