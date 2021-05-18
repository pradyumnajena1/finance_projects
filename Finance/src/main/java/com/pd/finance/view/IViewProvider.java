package com.pd.finance.view;

import com.pd.finance.model.Equity;
import com.pd.finance.model.portfolio.PortfolioEquity;

public interface  IViewProvider<T extends IView> {
    T getView(PortfolioEquity equity);
}
