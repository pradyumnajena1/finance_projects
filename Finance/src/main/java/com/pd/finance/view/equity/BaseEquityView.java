package com.pd.finance.view.equity;

import com.pd.finance.model.Equity;
import com.pd.finance.view.AbstractView;
import com.pd.finance.view.IView;
import com.pd.finance.view.IViewAttribute;
import com.pd.finance.view.equity.provider.EquityViewProvider;
import com.pd.finance.view.equity.provider.IEquityViewProvider;

import java.util.Map;

public class BaseEquityView extends AbstractView<Equity> {

    protected IEquityViewProvider viewProvider;

    public BaseEquityView(IEquityViewProvider viewProvider) {
        this.viewProvider = viewProvider;
    }

    public BaseEquityView(IView<Equity> decoratedView, EquityViewProvider viewProvider) {
        super(decoratedView);
        this.viewProvider = viewProvider;
    }

    protected void collectViewAttribute(Map<String, IViewAttribute<Equity>> attributeMap,IViewAttribute<Equity> viewAttribute) {
        Equity equity = viewProvider.getEquity();
        viewAttribute.setAttributeValue(viewAttribute.getValue(equity));
        attributeMap.put(viewAttribute.getAttributeName(), viewAttribute);
    }

    @Override
    protected void collectViewAttributes(Map<String, IViewAttribute<Equity>> stringIViewAttributeMap) {

    }
}
