package com.pd.finance.filter.db;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pd.finance.filter.EquityFilter;
import com.pd.finance.filter.FilterType;
import com.pd.finance.model.Equity;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FundamentalRatiosFilter extends AbstractDBFilter<Equity> implements EquityFilter {
    @JsonProperty("roceFilter")
    private RoceFilter roceFilter;

    @JsonProperty("debtorDaysFilter")
    private DebtorDaysFilter debtorDaysFilter;

    @JsonProperty("inventoryTurnoverFilter")
    private InventoryTurnoverFilter inventoryTurnoverFilter;

    public RoceFilter getRoceFilter() {
        return roceFilter;
    }

    public void setRoceFilter(RoceFilter roceFilter) {
        this.roceFilter = roceFilter;
    }

    public DebtorDaysFilter getDebtorDaysFilter() {
        return debtorDaysFilter;
    }

    public void setDebtorDaysFilter(DebtorDaysFilter debtorDaysFilter) {
        this.debtorDaysFilter = debtorDaysFilter;
    }

    public InventoryTurnoverFilter getInventoryTurnoverFilter() {
        return inventoryTurnoverFilter;
    }

    public void setInventoryTurnoverFilter(InventoryTurnoverFilter inventoryTurnoverFilter) {
        this.inventoryTurnoverFilter = inventoryTurnoverFilter;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.InDb;
    }

    @Override
    public Criteria getCriteria(String parentObject) {
        List<Criteria> criteriaList = new ArrayList<>();

        if(getRoceFilter()!=null){
            criteriaList.add(getRoceFilter().getCriteria("fundamentalRatios"));
        }
        if(getDebtorDaysFilter()!=null){
            criteriaList.add(getDebtorDaysFilter().getCriteria("fundamentalRatios"));
        }
        if(getInventoryTurnoverFilter()!=null){
            criteriaList.add(getInventoryTurnoverFilter().getCriteria("fundamentalRatios"));
        }

        return getAsCompositeCriteria(criteriaList);
    }

    @Override
    public boolean apply(Equity obj) {
        return false;
    }
}
