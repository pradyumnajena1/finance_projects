package com.pd.finance.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FundamentalRatios   extends EquityAttribute{
    @JsonProperty("roce")
    private Roce roce;

    @JsonProperty("debtorDays")
    private DebtorDays debtorDays;

    @JsonProperty("inventoryTurnOver")
    private InventoryTurnOver inventoryTurnOver;

    public Roce getRoce() {
        return roce;
    }

    public void setRoce(Roce roce) {
        this.roce = roce;
    }

    public DebtorDays getDebtorDays() {
        return debtorDays;
    }

    public void setDebtorDays(DebtorDays debtorDays) {
        this.debtorDays = debtorDays;
    }

    public InventoryTurnOver getInventoryTurnOver() {
        return inventoryTurnOver;
    }

    public void setInventoryTurnOver(InventoryTurnOver inventoryTurnOver) {
        this.inventoryTurnOver = inventoryTurnOver;
    }
}
