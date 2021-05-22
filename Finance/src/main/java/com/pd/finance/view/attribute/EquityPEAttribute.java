package com.pd.finance.view.attribute;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pd.finance.model.Equity;
import com.pd.finance.view.IViewAttribute;
import com.pd.finance.view.attribute.provider.AbstractEquityViewAttributeProvider;
import com.pd.finance.view.attribute.provider.IEquityViewAttributeProvider;

public class EquityPEAttribute extends BaseEquityViewAttribute implements IViewAttribute<Equity> {

    public EquityPEAttribute(IEquityViewAttributeProvider attributeProvider, String name, String displayName) {
        super(attributeProvider, name, displayName);
    }
    @Override
    @JsonIgnore
    public Object getValue(Equity equity) {
        return getAttributeProvider().getAttributeValue(equity);
    }
}
