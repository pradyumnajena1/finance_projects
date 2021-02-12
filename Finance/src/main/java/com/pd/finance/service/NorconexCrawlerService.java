package com.pd.finance.service;

import com.norconex.collector.http.HttpCollector;
import com.norconex.collector.http.HttpCollectorConfig;
import com.norconex.collector.http.crawler.HttpCrawlerConfig;
import com.pd.finance.config.ApplicationConfig;
import com.pd.finance.exceptions.PersistenceException;
import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.htmlscrapper.marketgainer.IMarketGainerEquityFactory;
import com.pd.finance.model.CrawlerResponse;
import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityIdentifier;
import com.pd.finance.request.MarketGainersWebCrawlRequest;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NorconexCrawlerService implements ICrawlerService {

    private static final Logger logger = LoggerFactory.getLogger(NorconexCrawlerService.class);
    @Autowired
    private IMarketGainerEquityFactory marketGainerEquityFactory;
    @Autowired
    private IEquityService equityService;

    @Autowired
    private ApplicationConfig config;
    @Autowired
    private IDocumentService documentService;
    @Autowired
    private IEquityEnricherService equityEnricherService;






    public HttpCollector getCrawler(String httpCollectorId, String httpCrawlerId, List<String> seedUrls, String logsDir) {
          final HttpCollectorConfig collectorConfig;
          final HttpCollector httpCollector;


        collectorConfig = getHttpCollectorConfig(httpCollectorId, httpCrawlerId, seedUrls, logsDir);
        httpCollector = new HttpCollector(collectorConfig);
        return httpCollector;


    }


    private HttpCollectorConfig getHttpCollectorConfig(String httpCollectorId, String httpCrawlerId, List<String> seedUrls, String logsDir) {
        HttpCollectorConfig collectorConfig = new HttpCollectorConfig();
        collectorConfig.setId(httpCollectorId);
        collectorConfig.setLogsDir(logsDir);

        HttpCrawlerConfig crawlerConfig = getHttpCrawlerConfig(httpCrawlerId, seedUrls);
        collectorConfig.setCrawlerConfigs(crawlerConfig);
        return collectorConfig;
    }

    @NotNull
    private HttpCrawlerConfig getHttpCrawlerConfig(String crawlerId, List<String> seedUrls) {
        HttpCrawlerConfig crawlerConfig = new HttpCrawlerConfig();
        crawlerConfig.setId(crawlerId);
        crawlerConfig.setStartURLs(seedUrls.toArray(new String[0]));
        return crawlerConfig;
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
            logger.error("Failed to execute GetGainers",ex);
            throw ex;
        }

    }

    private boolean fetchAndPersistEquity(Equity equity) throws ServiceException, PersistenceException {
        boolean success = false;
        try {
            EquityIdentifier identifier = new EquityIdentifier(equity.getName());
            identifier.putAdditionalAttribute("url",equity.getUrl());
            equityEnricherService.enrichEquity(identifier,equity);
            equityService.upsertEquity(equity);
            success = true;
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
