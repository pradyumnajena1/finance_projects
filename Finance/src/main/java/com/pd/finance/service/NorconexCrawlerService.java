package com.pd.finance.service;

import com.norconex.collector.http.HttpCollector;
import com.norconex.collector.http.HttpCollectorConfig;
import com.norconex.collector.http.crawler.HttpCrawlerConfig;
import com.norconex.collector.http.crawler.URLCrawlScopeStrategy;
import com.pd.finance.config.ApplicationConfig;
import com.pd.finance.exceptions.PersistenceException;
import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.htmlscrapper.listings.ICompanyListingsFactory;
import com.pd.finance.htmlscrapper.marketgainer.IMarketGainerEquityFactory;
import com.pd.finance.infra.IObjectConverter;
import com.pd.finance.model.*;
import com.pd.finance.request.CompanyListingWebCrawlRequest;
import com.pd.finance.request.FinancialSiteWebCrawlRequest;
import com.pd.finance.request.MarketGainersWebCrawlRequest;
import com.pd.finance.response.EquityStockExchangeDetailsResponse;
import com.pd.finance.utils.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NorconexCrawlerService implements ICrawlerService {

    private static final Logger logger = LoggerFactory.getLogger(NorconexCrawlerService.class);
    @Autowired
    private IMarketGainerEquityFactory marketGainerEquityFactory;

    @Autowired
    private ICompanyListingsFactory companyListingsFactory;

    @Autowired
    private IEquityService equityService;

    @Autowired
    private IStockExchangeService stockExchangeService;

    @Autowired
    private ApplicationConfig config;
    @Autowired
    private IDocumentService documentService;
    @Autowired
    private IEquityEnricherService equityEnricherService;
    @Autowired
    private IObjectConverter objectConverter;

    @Autowired
    private HttpCollector collectorA;

    @Autowired
    private HttpCollectorConfig collectorConfig;

    @Autowired
    private HttpCrawlerConfig crawlerConfig;


    public NorconexCrawlerService() {
    }


    @Override
    public CrawlerResponse crawlFinanceSites(FinancialSiteWebCrawlRequest crawlRequest) throws Exception {
        logger.info(String.format("crawlMarketGainers exec started"));
        try{
            crawlerConfig.setMaxDocuments(crawlRequest.getDebugFilter().getNumEquities());

            collectorA.start(false);

            CrawlerResponse crawlerResponse = new CrawlerResponse();

            crawlerResponse.setCompleted(true);
            return crawlerResponse;
        }catch (Exception ex){
            logger.error("Failed to execute GetGainers",ex);
            throw ex;
        }
    }


    @Override
    public CrawlerResponse crawlMarketGainers(MarketGainersWebCrawlRequest crawlRequest) throws Exception {
        logger.info(String.format("crawlMarketGainers exec started"));
        try{
            Document doc = getDocument();
            List<Equity> equityCollector = marketGainerEquityFactory.fetchMarketGainerEquities(doc,crawlRequest.getDebugFilter()!=null?crawlRequest.getDebugFilter().getNumEquities():-1);

            int successfulPersists = 0;
            for (Equity equity : equityCollector){
                boolean success = fetchAndPersistEquity(equity);
                if(success) successfulPersists++;
            }

            CrawlerResponse crawlerResponse = new CrawlerResponse();
            crawlerResponse.setNumObjectsCreated(equityCollector.size());
            crawlerResponse.setNumObjectsPersisted(successfulPersists);
            crawlerResponse.setCompleted(true);
            return crawlerResponse;
        }catch (Exception ex){
            logger.error("crawlMarketGainers exec failed",ex);
            throw ex;
        }

    }

    @Override
    public CrawlerResponse crawlCompanyListing(CompanyListingWebCrawlRequest crawlRequest) throws Exception{
        logger.info(String.format("crawlCompanyListing exec started"));
        try{
            Document doc = getCompanyListingDocument();
            List<String> companyNames = companyListingsFactory.getCompanyNames(doc,crawlRequest.getDebugFilter()!=null?crawlRequest.getDebugFilter().getNumEquities():-1);
            Collections.reverse(companyNames);
            int successfulPersists = 0;
            for (String companyName : companyNames){
                List<EquityStockExchangeDetails> existingDetails = stockExchangeService.findByName(companyName);

                if(CollectionUtils.isEmpty(existingDetails)){

                        boolean success = fetchAndPersistStockExchangeDetails(companyName);
                        if(success) successfulPersists++;
                }



            }

            CrawlerResponse crawlerResponse = new CrawlerResponse();
            crawlerResponse.setNumObjectsCreated(companyNames.size());
            crawlerResponse.setNumObjectsPersisted(successfulPersists);
            crawlerResponse.setCompleted(true);
            return crawlerResponse;
        }catch (Exception ex){
            logger.error("Failed to execute crawlCompanyListing",ex);
            throw ex;
        }
    }

    private boolean fetchAndPersistStockExchangeDetails(String companyName) {
        boolean success = false;
        Set<String> allowedExchanges = new HashSet<>(Arrays.asList("NSI", "BSE"));

        try {
            EquityIdentifier equityIdentifier = new EquityIdentifier(companyName,Constants.EXCHANGE_NSI,Constants.SOURCE_MONEY_CONTROL);

            EquityStockExchangeDetailsResponse stockExchangeDetails = stockExchangeService.getStockExchangeDetails(equityIdentifier);
            if(stockExchangeDetails!=null){


                stockExchangeService.create(objectConverter.convert(stockExchangeDetails));
                success=true;
            };





        } catch (Exception e) {
           logger.error(e.getMessage(),e);
        }
        return success;
    }

    private Document getCompanyListingDocument() throws Exception {
        String gainersUrl = config.getEnvProperty("CompanyNamesUrl");
        return documentService.getDocument(gainersUrl);
    }

    private boolean fetchAndPersistEquity(Equity equity) throws ServiceException, PersistenceException {
        boolean success = false;
        try {
            logger.info("fetchAndPersistEquity exec started for equity {}",equity.getEquityIdentifiers());


            EquityIdentifier identifier = equity.getEquityIdentifiers().getEquityIdentifier(Constants.SOURCE_MONEY_CONTROL);

            SourceDetails sourceDetails = equity.getSourceDetails().getSourceDetails(Constants.SOURCE_MONEY_CONTROL);

            if(sourceDetails!=null){

                identifier.putAdditionalAttribute("url",sourceDetails.getSourceUrl());
            };

            equityEnricherService.enrichEquity(identifier,equity);
            equityService.upsertEquity(equity);
            success = true;
            logger.info("fetchAndPersistEquity exec completed for equity {}",equity.getEquityIdentifiers());
        } catch (ServiceException e) {
           logger.error(e.getMessage(),e);
        } catch (PersistenceException e) {
            logger.error(e.getMessage(),e);
        }
        return success;
    }

    private Document getDocument() throws Exception {
        String gainersUrl = config.getEnvProperty("GainersUrl");
        return documentService.getDocument(gainersUrl);
    }
}
