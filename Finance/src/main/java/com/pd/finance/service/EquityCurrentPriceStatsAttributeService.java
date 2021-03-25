package com.pd.finance.service;

import com.pd.finance.config.ApplicationConfig;
import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.htmlscrapper.equity.IEquityEssentialsFactory;
import com.pd.finance.htmlscrapper.marketgainer.IEquityCurrentPriceStatsFactory;
import com.pd.finance.htmlscrapper.marketgainer.MarketGainerPageHelper;
import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityCurrentPriceStats;
import com.pd.finance.model.EquityIdentifier;
import com.pd.finance.model.SourceDetails;
import com.pd.finance.utils.CommonUtils;
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
public class EquityCurrentPriceStatsAttributeService extends HtmlScrapperEquityAttributeService{
    private static final Logger logger = LoggerFactory.getLogger(EquityCurrentPriceStatsAttributeService.class);

    @Autowired
    private IEquityCurrentPriceStatsFactory currentPriceStatsFactory;

    @Autowired
    private ApplicationConfig config;

    @Autowired
    public EquityCurrentPriceStatsAttributeService(IDocumentService documentService) {
        super(documentService);
    }

    @Override
    public void enrichEquity(EquityIdentifier identifier, Equity equity) throws ServiceException {
        logger.info( "enrichEquity started for equity: "+ equity.getEquityIdentifiers());
        try {
            Document document = getDocument(identifier);
            SourceDetails sourceDetails = equity.getSourceDetails().getSourceDetails(Constants.SOURCE_MONEY_CONTROL);
            if(sourceDetails!=null){

                try {
                    Node equityRow = MarketGainerPageHelper.getEquityRow(sourceDetails.getEquityName(), document);
                    EquityCurrentPriceStats equityCurrentPriceStats = currentPriceStatsFactory.createEquityCurrentPriceStats(equityRow);
                    equity.setEquityCurrentPriceStats(equityCurrentPriceStats);
                } catch (Exception ex) {
                    logger.error(ex.getMessage(),ex);
                }
            }


            logger.info( "enrichEquity completed for equity: "+ equity.getEquityIdentifiers());
        } catch (Exception e) {
           logger.error(e.getMessage(),e);
        }
    }

    protected Document getDocument(EquityIdentifier identifier) throws Exception {
        String gainersUrl = config.getEnvProperty("GainersUrl");
        return documentService.getDocument(gainersUrl);
    }



}
