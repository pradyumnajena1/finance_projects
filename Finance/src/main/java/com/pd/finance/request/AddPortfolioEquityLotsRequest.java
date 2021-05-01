package com.pd.finance.request;

import com.pd.finance.model.portfolio.PortfolioEquityLot;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

public class AddPortfolioEquityLotsRequest extends AbstractWebRequest{

    private List<PortfolioEquityLot> equityLots;

    public List<PortfolioEquityLot> getEquityLots() {
        return equityLots;
    }

    public void setEquityLots(List<PortfolioEquityLot> equityLots) {
        this.equityLots = equityLots;
    }

    @Override
    public void validate() {
        if(CollectionUtils.isEmpty(equityLots)){
            validationErrors.add("equityLots cant be empty");
        }
    }
}
