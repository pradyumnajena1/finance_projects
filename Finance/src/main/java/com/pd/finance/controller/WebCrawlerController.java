package com.pd.finance.controller;

import com.pd.finance.model.CrawlerResponse;
import com.pd.finance.request.CompanyListingWebCrawlRequest;
import com.pd.finance.request.FinancialSiteWebCrawlRequest;
import com.pd.finance.request.MarketGainersWebCrawlRequest;
import com.pd.finance.response.BaseResponse;
import com.pd.finance.service.ICrawlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class WebCrawlerController {

    private Logger logger = LoggerFactory.getLogger(WebCrawlerController.class);
    @Autowired
    private ICrawlerService crawlerService;


    @RequestMapping(value = "crawl/market/gainers",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse crawlMarketGainers(@RequestBody MarketGainersWebCrawlRequest request) {

        try {

          CrawlerResponse crawlerResponse =  crawlerService.crawlMarketGainers(request);
           return new BaseResponse(crawlerResponse);
        } catch (Exception e) {
            logger.error("Failed to process Gainers",e);
            return  new BaseResponse(e);
        }

    }




    @RequestMapping(value = "crawl/financesites",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse crawlFinancialSites(@RequestBody FinancialSiteWebCrawlRequest request) {

        try {

            CrawlerResponse crawlerResponse =  crawlerService.crawlFinanceSites(request);
            return new BaseResponse(crawlerResponse);
        } catch (Exception e) {
            logger.error("Failed to process Gainers",e);
            return  new BaseResponse(e);
        }

    }


    @RequestMapping(value = "crawl/companylistings",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse crawlCompanyListings(@RequestBody CompanyListingWebCrawlRequest request) {

        try {

            CrawlerResponse crawlerResponse =  crawlerService.crawlCompanyListing(request);
            return new BaseResponse(crawlerResponse);
        } catch (Exception e) {
            logger.error("Failed to process crawlCompanyListings",e);
            return  new BaseResponse(e);
        }

    }


}
