package com.pd.finance.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsiderTransactionDetails extends EquityAttribute {

    @JsonProperty("purchases")
    private List<InsiderTransaction> purchases;

    @JsonProperty("sales")
    private List<InsiderTransaction> sales;

    public List<InsiderTransaction> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<InsiderTransaction> purchases) {
        this.purchases = purchases;
    }

    public List<InsiderTransaction> getSales() {
        return sales;
    }

    public void setSales(List<InsiderTransaction> sales) {
        this.sales = sales;
    }
}
