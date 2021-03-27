package com.pd.finance.service;

import com.pd.finance.config.ApplicationConfig;
import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.htmlscrapper.marketgainer.IMarketGainerEquityPerformancesFactory;
import com.pd.finance.htmlscrapper.marketgainer.MarketGainerPageHelper;
import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityIdentifier;
import com.pd.finance.model.SourceDetails;
import com.pd.finance.utils.Constants;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EquityRecentPerformancesAttributeService extends HtmlScrapperEquityAttributeService{
    private static final Logger logger = LoggerFactory.getLogger(EquityEssentialsAttributeService.class);

    @Autowired
    private IMarketGainerEquityPerformancesFactory marketGainerEquityPerformancesFactory;
    @Autowired
    private ApplicationConfig config;

    @Autowired
    public EquityRecentPerformancesAttributeService(IDocumentService documentService) {
        super(documentService);
    }

    @Override
    public void enrichEquity(EquityIdentifier identifier, Equity equity) throws ServiceException {
        logger.info( "enrichEquity started for equity: "+ equity.getEquityIdentifiers());
        try {
            Document document = getDocument(identifier);
            SourceDetails sourceDetails = equity.getSourceDetails(Constants.SOURCE_MONEY_CONTROL);

            if(sourceDetails!=null){
                try {
                    Node equityRow =MarketGainerPageHelper.getEquityRow(sourceDetails.getEquityName(), document);
                    equity.setPerformances(marketGainerEquityPerformancesFactory.createMarketGainerEquityPerformances(equityRow));
                } catch (Exception exception) {
                    logger.error(exception.getMessage(),exception);
                }
            }



            logger.info( "enrichEquity completed for equity: "+ equity.getEquityIdentifiers());
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw   new ServiceException(e);
        }
    }

    protected Document getDocument(EquityIdentifier identifier) throws Exception {
        String gainersUrl = config.getEnvProperty("GainersUrl");
        return documentService.getDocument(gainersUrl);
    }

}
