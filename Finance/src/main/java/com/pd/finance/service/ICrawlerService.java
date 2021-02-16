package com.pd.finance.service;

import com.pd.finance.model.CrawlerResponse;
import com.pd.finance.request.CompanyListingWebCrawlRequest;
import com.pd.finance.request.FinancialSiteWebCrawlRequest;
import com.pd.finance.request.MarketGainersWebCrawlRequest;

public interface ICrawlerService {




    CrawlerResponse crawlFinanceSites(FinancialSiteWebCrawlRequest crawlRequest) throws Exception;

    CrawlerResponse crawlMarketGainers(MarketGainersWebCrawlRequest crawlRequest) throws Exception;

    CrawlerResponse crawlCompanyListing(CompanyListingWebCrawlRequest crawlRequest) throws Exception;
}
