package com.pd.finance.view.portfolio;

import com.pd.finance.model.Equity;
import com.pd.finance.model.portfolio.Portfolio;
import com.pd.finance.model.portfolio.PortfolioEquity;
import com.pd.finance.view.IViewAttribute;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

public class PortfolioView implements IPortfolioView {
    private PortfolioEquity equity;

    public PortfolioView(PortfolioEquity equity) {
        this.equity = equity;
    }

    @Override
    public long getShares() {
        return 0;
    }

    @Override
    public Date getTradeDate() {
        return null;
    }

    @Override
    public BigDecimal getHighLimit() {
        return null;
    }

    @Override
    public BigDecimal getLowLimit() {
        return null;
    }

    @Override
    public BigDecimal getCostPerShare() {
        return null;
    }

    @Override
    public BigDecimal getMarketValue() {
        return null;
    }

    @Override
    public BigDecimal getTotalChange() {
        return null;
    }

    @Override
    public BigDecimal getTotalChangePercentage() {
        return null;
    }

    @Override
    public BigDecimal getDayChange() {
        return null;
    }

    @Override
    public BigDecimal getDayChangePercentage() {
        return null;
    }

    @Override
    public Set<String> getNotes() {
        return null;
    }

    @Override
    public long getNoOfLots() {
        return 0;
    }

    @Override
    public BigDecimal getAnnualisedGains() {
        return null;
    }
}
