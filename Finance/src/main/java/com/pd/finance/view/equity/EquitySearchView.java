package com.pd.finance.view.equity;

import com.pd.finance.model.Equity;
import com.pd.finance.view.IView;
import com.pd.finance.view.IViewAttribute;
import com.pd.finance.view.equity.provider.EquityViewProvider;
import com.pd.finance.view.equity.provider.IEquityViewProvider;

import java.util.Map;

public class EquitySearchView extends BaseEquityView{

    public EquitySearchView(IEquityViewProvider viewProvider) {
        super(viewProvider);
    }

    public EquitySearchView(IView<Equity> decoratedView, EquityViewProvider viewProvider) {
        super(decoratedView, viewProvider);
    }


    @Override
    protected void collectViewAttributes(Map<String, IViewAttribute<Equity>> viewAttributeMap) {
        collectViewAttribute(viewAttributeMap, viewProvider.getEquityUrlAttribute());
        collectViewAttribute(viewAttributeMap, viewProvider.getEquityNameAttribute());
        collectViewAttribute(viewAttributeMap, viewProvider.getEquityPEAttribute());
        collectViewAttribute(viewAttributeMap, viewProvider.getEquityMarketCapAttribute());
    }


}
