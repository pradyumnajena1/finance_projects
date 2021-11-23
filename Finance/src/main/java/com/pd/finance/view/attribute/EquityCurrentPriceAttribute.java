package com.pd.finance.view.attribute;

import com.pd.finance.model.Equity;
import com.pd.finance.view.attribute.provider.IEquityViewAttributeProvider;

public class EquityCurrentPriceAttribute extends BaseEquityViewAttribute{
    public EquityCurrentPriceAttribute(IEquityViewAttributeProvider attributeProvider, String name, String displayName) {
        super(attributeProvider, name, displayName);
    }

    @Override
    public Object getValue(Equity equity) {
      return  getAttributeProvider().getAttributeValue(equity);
    }
}
