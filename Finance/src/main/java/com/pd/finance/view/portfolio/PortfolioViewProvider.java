package com.pd.finance.view.portfolio;
import com.pd.finance.model.Equity;
import com.pd.finance.model.portfolio.PortfolioEquity;
import com.pd.finance.view.IViewProvider;


public class PortfolioViewProvider implements  IViewProvider<PortfolioView> {

    @Override
    public PortfolioView getView(PortfolioEquity equity) {
        return new PortfolioView(equity);
    }
}
