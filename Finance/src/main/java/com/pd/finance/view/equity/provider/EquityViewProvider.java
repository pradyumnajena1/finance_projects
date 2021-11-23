package com.pd.finance.view.equity.provider;

import com.pd.finance.model.Equity;
import com.pd.finance.view.IViewAttribute;
import com.pd.finance.view.attribute.EquityMarketCapAttribute;
import com.pd.finance.view.attribute.EquityNameAttribute;
import com.pd.finance.view.attribute.EquityPEAttribute;
import com.pd.finance.view.attribute.provider.*;
import com.pd.finance.view.attribute.EquityUrlAttribute;
import org.jetbrains.annotations.NotNull;

public class EquityViewProvider implements IEquityViewProvider {

    private Equity equity;
    private IEquityViewAttributeProvider urlAttributeProvider = new EquityUrlAttributeProvider( );
    private IEquityViewAttributeProvider nameAttributeProvider = new EquityNameAttributeProvider( );
    private IEquityViewAttributeProvider peAttributeProvider = new EquityPEAttributeProvider( );
    private IEquityViewAttributeProvider marketCapAttributeProvider = new EquityMarketCapAttributeProvider( );
    private IEquityViewAttributeProvider currentPriceAttributeProvider = new EquityCurrentPriceAttributeProvider( );

    public EquityViewProvider(Equity equity) {
        this.equity = equity;
    }


    @Override
    public Equity getEquity() {
        return equity;
    }
    @Override
    @NotNull
    public IViewAttribute<Equity> getEquityUrlAttribute() {
        IViewAttribute<Equity> urlAttribute = new EquityUrlAttribute(urlAttributeProvider,"Url","Url");
        return urlAttribute;
    }
    @Override
    @NotNull
    public IViewAttribute<Equity> getEquityNameAttribute() {
        IViewAttribute<Equity> nameAttribute = new EquityNameAttribute(nameAttributeProvider,"Name","Name");
        return nameAttribute;
    }
    @Override
    @NotNull
    public IViewAttribute<Equity> getEquityPEAttribute() {
        IViewAttribute<Equity> peAttribute = new EquityPEAttribute(peAttributeProvider,"PE","PE");
        return peAttribute;
    }
    @Override
    @NotNull
    public IViewAttribute<Equity> getEquityMarketCapAttribute() {
        IViewAttribute<Equity> marketCapAttribute = new EquityMarketCapAttribute(marketCapAttributeProvider,"MarketCap","MarketCap");
        return marketCapAttribute;
    }

    @Override
    @NotNull
    public IViewAttribute<Equity> getEquityCurrentPriceAttribute() {
        IViewAttribute<Equity> currentPriceAttribute = new EquityMarketCapAttribute(currentPriceAttributeProvider,"CurrentPrice","CurrentPrice");
        return currentPriceAttribute;
    }
}
