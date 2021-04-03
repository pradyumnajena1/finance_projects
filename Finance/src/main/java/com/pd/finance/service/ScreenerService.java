package com.pd.finance.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.pd.finance.config.ApplicationConfig;
import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.model.EquityIdentifier;
import com.pd.finance.model.WebDocument;
import com.pd.finance.response.chart.ScreenerEquitySearchResponseLineItem;
import com.pd.finance.utils.JsonUtils;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.Period;
import java.util.List;

@Service
public class ScreenerService extends AbstractHttpService implements IScreenerService {
    private static final Logger logger = LoggerFactory.getLogger(YahooService.class);


    @Autowired
    private ApplicationConfig config;
    @Autowired
    private IDocumentService documentService;

    @Override
    public List<ScreenerEquitySearchResponseLineItem> findEquity(EquityIdentifier equityIdentifier) throws ServiceException {

        List<ScreenerEquitySearchResponseLineItem> result = null;
        try {
            String searchString = equityIdentifier.getSearchString();
            String url = MessageFormat.format(config.getEnvProperty("ScreenerEquitySearchUrl"), searchString);
            WebDocument webDocument = documentService.getWebDocument(url, Period.ofDays(7));
            if(webDocument!=null){
                result =   JsonUtils.deserialize(webDocument.getContent(),new TypeReference<List<ScreenerEquitySearchResponseLineItem>>() {});
            }

        } catch (Exception exception) {
            logger.error(exception.getMessage(),exception);
            throw new ServiceException(exception);
        }
        return result;
    }
}
