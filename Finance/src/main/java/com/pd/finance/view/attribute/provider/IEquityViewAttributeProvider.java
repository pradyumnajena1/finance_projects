package com.pd.finance.view.attribute.provider;

import com.pd.finance.model.Equity;
import com.pd.finance.view.IViewAttribute;

public interface IEquityViewAttributeProvider {

    Object getAttributeValue(Equity equity);
}
