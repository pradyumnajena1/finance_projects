package com.pd.finance.view.attribute.provider;

import com.pd.finance.model.Equity;

public abstract class AbstractEquityViewAttributeProvider implements IEquityViewAttributeProvider {



    public abstract Object getAttributeValue(Equity equity) ;
}
