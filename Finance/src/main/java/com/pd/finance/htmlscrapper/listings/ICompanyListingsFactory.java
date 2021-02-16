package com.pd.finance.htmlscrapper.listings;

import org.jsoup.nodes.Document;

import java.util.List;

public interface ICompanyListingsFactory {
    List<String> getCompanyNames(Document document,int numCompanies);
}
