package com.pd.finance.htmlscrapper.listings;

import com.pd.finance.model.Equity;

import java.util.List;

public interface ICompanyListingsFactory {
    List<Equity> getEquities(int numEquitiesToAdd);
}
