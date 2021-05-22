package com.pd.finance.view.attribute;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pd.finance.model.Equity;
import com.pd.finance.view.IViewAttribute;
import com.pd.finance.view.attribute.provider.IEquityViewAttributeProvider;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EquityNameAttribute extends BaseEquityViewAttribute implements IViewAttribute<Equity> {


    public EquityNameAttribute(IEquityViewAttributeProvider attributeProvider, String name, String displayName) {
        super(attributeProvider, name, displayName);
    }


    @Override
    @JsonIgnore
    public Object getValue(Equity equity) {
        return getAttributeProvider().getAttributeValue(equity);
    }
}
