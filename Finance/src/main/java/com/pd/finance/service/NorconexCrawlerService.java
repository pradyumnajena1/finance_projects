package com.pd.finance.service;

import com.norconex.collector.http.HttpCollector;
import com.norconex.collector.http.HttpCollectorConfig;
import com.norconex.collector.http.crawler.HttpCrawlerConfig;
import com.pd.finance.config.ApplicationConfig;
import com.pd.finance.exceptions.PersistenceException;
import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.htmlscrapper.listings.ICompanyListingsFactory;
import com.pd.finance.htmlscrapper.marketgainer.IMarketGainerEquityFactory;
import com.pd.finance.htmlscrapper.marketloser.IMarketLoserEquityFactory;
import com.pd.finance.infra.IObjectConverter;
import com.pd.finance.model.*;
import com.pd.finance.request.CompanyListingWebCrawlRequest;
import com.pd.finance.request.FinancialSiteWebCrawlRequest;
import com.pd.finance.request.MarketGainersWebCrawlRequest;
import com.pd.finance.request.MarketLosersWebCrawlRequest;
import com.pd.finance.response.EquityStockExchangeDetailsResponse;
import com.pd.finance.service.equityenricher.IEquityEnricherService;
import com.pd.finance.utils.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NorconexCrawlerService implements ICrawlerService {

    private static final Logger logger = LoggerFactory.getLogger(NorconexCrawlerService.class);
    @Autowired
    private IMarketGainerEquityFactory marketGainerEquityFactory;

    @Autowired
    private IMarketLoserEquityFactory marketLoserEquityFactory;

    @Autowired
    private ICompanyListingsFactory companyListingsFactory;

    @Autowired
    private IEquityService equityService;

    @Autowired
    private IYahooService stockExchangeService;

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
            Document doc = getDocument(crawlRequest);
            int maxEquitiesToFetch = crawlRequest.getDebugFilter() != null ? crawlRequest.getDebugFilter().getNumEquities() : -1;
            List<Equity> marketGainerEquities = marketGainerEquityFactory.fetchMarketGainerEquities(doc, maxEquitiesToFetch,crawlRequest.getExchange());

            int numObjectsCreated = marketGainerEquities.size();
            int successfulPersists = 0;

            Equity[] equities = marketGainerEquities.toArray(new Equity[numObjectsCreated]);

            for(int i=0;i<numObjectsCreated;i++){

                boolean success = fetchAndPersistEquity(equities[i]);
                if(success){
                    successfulPersists++;
                    logger.info("crawlMarketGainers successfully fetch And Persisted {} Equities out of {} equities ",successfulPersists,numObjectsCreated);
                }
                equities[i] = null;
            }
            CrawlerResponse crawlerResponse = getCrawlerResponse(numObjectsCreated, successfulPersists);
            logger.info(String.format("crawlMarketGainers exec completed"));
            return crawlerResponse;
        }catch (Exception ex){
            logger.error("crawlMarketGainers exec failed",ex);
            throw ex;
        }

    }

    @Override
    public CrawlerResponse crawlMarketLosers(MarketLosersWebCrawlRequest crawlRequest) throws Exception {
        logger.info(String.format("crawlMarketLosers exec started"));
        try{
            Document doc = getDocument(crawlRequest);
            int maxEquitiesToFetch = crawlRequest.getDebugFilter() != null ? crawlRequest.getDebugFilter().getNumEquities() : -1;

            List<Equity> marketLoserEquities = marketLoserEquityFactory.fetchMarketLoserEquities(doc, maxEquitiesToFetch,crawlRequest.getExchange());

            int numObjectsCreated = marketLoserEquities.size();
            int successfulPersists = 0;

            Equity[] equities = marketLoserEquities.toArray(new Equity[numObjectsCreated]);

            for(int i=0;i<numObjectsCreated;i++){

                boolean success = fetchAndPersistEquity(equities[i]);
                if(success){

                    successfulPersists++;
                    logger.info("crawlMarketLosers successfully fetch And Persisted {} Equities out of {} equities ",successfulPersists,numObjectsCreated);
                }
                equities[i] = null;
            }
            CrawlerResponse crawlerResponse = getCrawlerResponse(numObjectsCreated, successfulPersists);
            logger.info(String.format("crawlMarketLosers exec completed"));
            return crawlerResponse;
        }catch (Exception ex){
            logger.error("crawlMarketLosers exec failed",ex);
            throw ex;
        }

    }



    private boolean fetchAndPersistEquity(Equity equity) throws ServiceException, PersistenceException {
        boolean success = false;
        try {
            logger.info("fetchAndPersistEquity exec started for equity {}",equity.getEquityIdentifiers());

            EquityIdentifier identifier = equity.getDefaultEquityIdentifier();
            SourceDetails sourceDetails = equity.getDefaultSourceDetails();
            if(sourceDetails!=null){
                identifier.putAdditionalAttribute("url",sourceDetails.getSourceUrl());
            };
            Equity equityFromDb = equityService.getEquity(identifier);
            if(equityFromDb==null){
                equityService.upsertEquity(equity);
                success = true;
            }


            logger.info("fetchAndPersistEquity exec completed for equity {}",equity.getEquityIdentifiers());
        } catch ( Exception e) {
            logger.error(e.getMessage(),e);
        }
        return success;
    }

    @NotNull
    protected CrawlerResponse getCrawlerResponse(int numObjectsCreated, int successfulPersists) {
        CrawlerResponse crawlerResponse = new CrawlerResponse();
        crawlerResponse.setNumObjectsCreated(numObjectsCreated);
        crawlerResponse.setNumObjectsPersisted(successfulPersists);
        crawlerResponse.setCompleted(true);
        return crawlerResponse;
    }

    @Override
    public CrawlerResponse crawlCompanyListing(CompanyListingWebCrawlRequest crawlRequest) throws Exception{
        logger.info(String.format("crawlCompanyListing exec started"));
        try{
            Document doc = getCompanyListingDocument();

            List<String> companyNames = companyListingsFactory.getCompanyNames(doc,crawlRequest.getDebugFilter()!=null?crawlRequest.getDebugFilter().getNumEquities():-1);
            Collections.reverse(companyNames);
            int numObjectsCreated = companyNames.size();
            int successfulPersists = 0;

            for (String companyName : companyNames){
                List<EquityStockExchangeDetails> existingDetails = stockExchangeService.findByName(companyName);

                if(CollectionUtils.isEmpty(existingDetails)){

                    EquityIdentifier equityIdentifier = new EquityIdentifier(companyName, Constants.EXCHANGE_NSI, Constants.SOURCE_MONEY_CONTROL);
                    boolean success = fetchAndPersistStockExchangeDetails(equityIdentifier);
                    if(success) successfulPersists++;
                }



            }

            CrawlerResponse crawlerResponse = getCrawlerResponse(numObjectsCreated, successfulPersists);
            return crawlerResponse;
        }catch (Exception ex){
            logger.error("Failed to execute crawlCompanyListing",ex);
            throw ex;
        }
    }

    private boolean fetchAndPersistStockExchangeDetails(EquityIdentifier equityIdentifier) {
        boolean success = false;
        try {


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
        return documentService.getDocument(gainersUrl,null);
    }



    private Document getDocument(MarketGainersWebCrawlRequest crawlRequest) throws Exception {
        String gainersUrl = config.getEnvProperty("NSEGainersUrl");

        if(Constants.EXCHANGE_BSE.equalsIgnoreCase(crawlRequest.getExchange())){

              gainersUrl = config.getEnvProperty("BSEGainersUrl");
        }

        return documentService.getDocument(gainersUrl,null);
    }
    private Document getDocument(MarketLosersWebCrawlRequest crawlRequest) throws Exception {
        String gainersUrl = config.getEnvProperty("NSELosersUrl");

        if(Constants.EXCHANGE_BSE.equalsIgnoreCase(crawlRequest.getExchange())){

            gainersUrl = config.getEnvProperty("BSELosersUrl");
        }

        return documentService.getDocument(gainersUrl,null);
    }
}
