package com.pd.finance.config;

import com.norconex.collector.http.HttpCollector;
import com.norconex.collector.http.HttpCollectorConfig;
import com.norconex.collector.http.crawler.HttpCrawlerConfig;
import com.norconex.collector.http.crawler.URLCrawlScopeStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class NorconexConfig {

    @Value("${CollectId}")
    private String collectorId;

    @Value("${CrawlerId}")
    private String crawlerId;

    @Value("${LogsDir}")
    private String logsDIr;

    @Value("${ProgressDir}")
    private String progressDir;

    @Value("${CrawlerWorkDir}")
    private String crawlerWorkDir;



    @Bean
    public HttpCollector getHttpCollector(){
        HttpCollectorConfig httpCollectorConfig = getHttpCollectorConfig();
        HttpCollector httpCollector = new HttpCollector(httpCollectorConfig);
        return httpCollector;
    }

    @Bean
    public HttpCollectorConfig getHttpCollectorConfig() {
        HttpCollectorConfig collectorConfig = new HttpCollectorConfig();
        collectorConfig.setId(collectorId);

        collectorConfig.setLogsDir(logsDIr);
        collectorConfig.setProgressDir(progressDir);

        HttpCrawlerConfig crawlerConfig = getHttpCrawlerConfig();
        collectorConfig.setCrawlerConfigs(crawlerConfig);

        return collectorConfig;
    }

    @Bean
    public HttpCrawlerConfig getHttpCrawlerConfig() {
        HttpCrawlerConfig crawlerConfig = new HttpCrawlerConfig();
        crawlerConfig.setId(crawlerId);

        URLCrawlScopeStrategy urlCrawlScopeStrategy = getUrlCrawlScopeStrategy();
        crawlerConfig.setUrlCrawlScopeStrategy(urlCrawlScopeStrategy);
        crawlerConfig.setWorkDir(new File(crawlerWorkDir));

        return crawlerConfig;
    }

    @Bean
    public URLCrawlScopeStrategy getUrlCrawlScopeStrategy() {
        URLCrawlScopeStrategy urlCrawlScopeStrategy = new URLCrawlScopeStrategy();
        urlCrawlScopeStrategy.setStayOnDomain(true);
        urlCrawlScopeStrategy.setStayOnPort(true);
        urlCrawlScopeStrategy.setStayOnProtocol(true);
        return urlCrawlScopeStrategy;
    }
}
