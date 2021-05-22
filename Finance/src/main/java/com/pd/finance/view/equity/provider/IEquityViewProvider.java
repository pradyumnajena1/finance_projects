package com.pd.finance.view.equity.provider;

import com.pd.finance.model.Equity;
import com.pd.finance.view.IViewAttribute;
import com.pd.finance.view.attribute.EquityNameAttribute;
import com.pd.finance.view.attribute.EquityUrlAttribute;
import org.jetbrains.annotations.NotNull;

public interface IEquityViewProvider {
    Equity getEquity();

    @NotNull
    IViewAttribute<Equity> getEquityUrlAttribute();

    @NotNull
    IViewAttribute<Equity> getEquityNameAttribute();

    @NotNull
    IViewAttribute<Equity> getEquityPEAttribute();

    @NotNull
    IViewAttribute<Equity> getEquityMarketCapAttribute();
}
