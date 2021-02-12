package com.pd.finance.service;

import com.pd.finance.model.CrawlerResponse;
import com.pd.finance.request.MarketGainersWebCrawlRequest;

public interface ICrawlerService {


    CrawlerResponse crawlMarketGainers(MarketGainersWebCrawlRequest crawlRequest) throws Exception;
}
