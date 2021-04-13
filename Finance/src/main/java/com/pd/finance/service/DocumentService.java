package com.pd.finance.service;

import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.model.EquityIdentifier;
import com.pd.finance.model.WebDocument;
import com.pd.finance.persistence.WebDocumentRepository;
import okhttp3.Interceptor;
import org.apache.commons.collections.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class DocumentService extends AbstractHttpService implements IDocumentService{
    private static final Logger logger = LoggerFactory.getLogger(DocumentService.class);


    @Autowired
    private WebDocumentRepository documentRepository;

    @Resource(name="rateLimitInterceptor")
    private Interceptor rateLimitInterceptor;

    public DocumentService() {

    }

    @Override
    public Document getDocument(String url, TemporalAmount ttl) throws ServiceException {
        try {

            WebDocument result = getWebDocument(url, ttl);
            String content = result.getContent();
            Document document = Jsoup.parse(content);
            return document;
        } catch (Exception exception) {
            logger.error(exception.getMessage(),exception);
            throw new ServiceException(exception);
        }
    }
    @Override
    public WebDocument getWebDocument(String url, TemporalAmount ttl) {
        WebDocument result = null;

        try {
            WebDocument existingWebDocument = null;
            List<WebDocument> byUrl = documentRepository.findByUrl(url);
            if(CollectionUtils.isNotEmpty(byUrl)){

                  existingWebDocument = byUrl.get(0);
            }
            result = existingWebDocument ;
            if(isUpdateRequired(existingWebDocument)){

                String content = get(url);
                WebDocument newWebDocument = createWebDocument(url, content, ttl);
                if(existingWebDocument==null){
                    documentRepository.insert(newWebDocument);
                }else{
                    newWebDocument.setId(existingWebDocument.getId());
                    documentRepository.save(newWebDocument);
                }
                result = newWebDocument;

            }
        } catch (Exception exception) {
            logger.error(exception.getMessage(),exception);
        }
        return result;
    }

    protected boolean isUpdateRequired(WebDocument webDocument) {
        if(webDocument==null){
            return true;
        }
        Instant instant = Instant.now();

        Instant expiredOn = Instant.ofEpochMilli(webDocument.getExpireDate().getTime());

        return   instant.isAfter(expiredOn) ;
    }

    @NotNull
    protected WebDocument createWebDocument(String url, String content, TemporalAmount expireAfter) {
        WebDocument webDocument;
        webDocument = new WebDocument();
        webDocument.setUrl(url);
        webDocument.setContent(content);
        Date createdDate = new Date();
        webDocument.setCreatedDate(createdDate);
        Date expireDate = createdDate;

        if(expireAfter!=null){
            Instant instant = Instant.ofEpochMilli(createdDate.getTime());
            instant = instant.plus(expireAfter);
            expireDate = new Date(instant.toEpochMilli());

        }
        webDocument.setExpireDate(expireDate);
        return webDocument;
    }

    @Override
    public Document getDocument(EquityIdentifier identifier,TemporalAmount ttl) throws Exception {
        Document document = null;
        if (identifier!=null) {
            String url = (String) identifier.getAdditionalAttribute("url");
            document = getDocument(url, ttl);
        }
        return document;
    }

    @Override
    protected List<Interceptor> getInterceptors() {
        return new ArrayList<>(Arrays.asList(rateLimitInterceptor));
    }
}
