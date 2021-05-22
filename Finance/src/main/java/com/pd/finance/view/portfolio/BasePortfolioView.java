package com.pd.finance.view.portfolio;

import com.pd.finance.model.Equity;
import com.pd.finance.model.portfolio.Portfolio;
import com.pd.finance.view.AbstractView;
import com.pd.finance.view.IView;
import com.pd.finance.view.IViewAttribute;

import java.util.Map;

public class BasePortfolioView extends AbstractView<Portfolio> {

    public BasePortfolioView() {
    }

    public BasePortfolioView(IView<Portfolio> decoratedView) {
        super(decoratedView);
    }

    @Override
    protected void collectViewAttributes(Map<String, IViewAttribute<Portfolio>> stringIViewAttributeMap) {

    }


}
