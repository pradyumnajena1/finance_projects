package com.pd.finance.view.attribute.provider;

import com.pd.finance.model.Equity;

public class EquityMarketCapAttributeProvider extends AbstractEquityViewAttributeProvider{

    @Override
    public Object getAttributeValue(Equity equity) {
        return equity.getOverview().getMarketCap();
    }
}
