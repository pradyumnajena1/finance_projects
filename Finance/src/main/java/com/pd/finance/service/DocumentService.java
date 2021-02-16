package com.pd.finance.service;

import com.pd.finance.htmlscrapper.marketgainer.MarketGainerEquityFactory;
import com.pd.finance.model.EquityIdentifier;
import okhttp3.Interceptor;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DocumentService extends AbstractHttpService implements IDocumentService{
    private static final Logger logger = LoggerFactory.getLogger(DocumentService.class);
    @Autowired
    private ICacheService cacheService;

    @Resource(name="rateLimitInterceptor")
    private Interceptor rateLimitInterceptor;

    public DocumentService() {

    }

    @Override
    public Document getDocument(String url) throws Exception {
       // logger.info("getDocument started executing for url {}",url);
        Document document = cacheService.getDocument(url,anUrl-> doGetDocument(anUrl));
       // logger.info("getDocument completed executing for url {}",url);
        return document;
    }

    @Override
    public Document getDocument(EquityIdentifier identifier) throws Exception {
        return getDocument((String) identifier.getAdditionalAttribute("url"));
    }

    private Document doGetDocument(String anUrl) {
        Document document = null;
        try {
            logger.warn("getDocument dint find in cache , fetching from source for url {}",anUrl);
            String responseString = get(anUrl);
            if(StringUtils.isNotBlank(responseString)){
                document =  Jsoup.parse(responseString);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            document = null;
        }
        return document;
    }

    @Override
    protected List<Interceptor> getInterceptors() {
        return new ArrayList<>(Arrays.asList(rateLimitInterceptor));
    }
}
