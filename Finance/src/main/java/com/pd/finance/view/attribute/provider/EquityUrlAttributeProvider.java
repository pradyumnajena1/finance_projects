package com.pd.finance.view.attribute.provider;

import com.pd.finance.model.Equity;
import com.pd.finance.view.attribute.provider.AbstractEquityViewAttributeProvider;

public class EquityUrlAttributeProvider extends AbstractEquityViewAttributeProvider {

    public EquityUrlAttributeProvider( ) {

    }

    @Override
    public Object getAttributeValue(Equity equity) {
        return "/equities/"+equity.getId();
    }
}
