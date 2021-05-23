package com.pd.finance.service.equityattribute;

import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.htmlscrapper.equity.IEquityFundamentalRatioFactory;
import com.pd.finance.htmlscrapper.equity.IShareholdingPatternFactory;
import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityIdentifier;
import com.pd.finance.service.IDocumentService;
import com.pd.finance.utils.Constants;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FundamentalRationAttributeService  extends HtmlScrapperEquityAttributeService{
    private static final Logger logger = LoggerFactory.getLogger(ShareholdingPatternAttributeService.class);
    public static final int NUM_DAYS_TO_CACHE = 5;
    @Autowired
    private IEquityFundamentalRatioFactory fundamentalRatioFactory;

    public FundamentalRationAttributeService(IDocumentService documentService) {
        super(documentService);
    }

    @Override
    public void enrichEquity(EquityIdentifier identifier, Equity equity) throws ServiceException {
        logger.info( "enrichEquity completed for equity: "+ equity.getDefaultEquityIdentifier());
        try {
            EquityIdentifier screenerEquityIdentifier = equity.getEquityIdentifier(Constants.SOURCE_SCREENER_IO);
            if (screenerEquityIdentifier!=null) {

                Document document = getDocument(screenerEquityIdentifier);
                equity.setFundamentalRatios(fundamentalRatioFactory.create(document));
            }
            logger.info( "enrichEquity completed for equity: "+ equity.getDefaultEquityIdentifier());
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }
}
