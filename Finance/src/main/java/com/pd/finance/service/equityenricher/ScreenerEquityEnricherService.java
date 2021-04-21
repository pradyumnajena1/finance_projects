package com.pd.finance.service.equityenricher;

import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityIdentifier;
import com.pd.finance.model.SourceDetails;
import com.pd.finance.response.chart.ScreenerEquitySearchResponseLineItem;
import com.pd.finance.service.IEquityService;
import com.pd.finance.service.IScreenerService;
import com.pd.finance.service.equityattribute.IEquityAttributeService;
import com.pd.finance.utils.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ScreenerEquityEnricherService  extends AbstractEquityEnricherService implements IEquityEnricherService {
    private static final Logger logger = LoggerFactory.getLogger(ScreenerEquityEnricherService.class);


    @Autowired
    private IEquityService equityService;
    @Autowired
    private IScreenerService screenerService;


    @Resource(name = "equityProfitLossAttributeService")
    private IEquityAttributeService profitLossAttributeService;


    @Override
    public void enrichEquity(EquityIdentifier defaultEquityIdentifier, Equity equity, boolean forceUpdate) throws ServiceException {
        try {
            logger.info("enrichEquity exec started for equity:{}",equity.getDefaultEquityIdentifier());
            Equity equityFromDb = equityService.getEquity(defaultEquityIdentifier);

            updateEquityIdentityAndSourceDetails(defaultEquityIdentifier, equity);
            addProfitLossDetails(defaultEquityIdentifier,equity,equityFromDb,forceUpdate);


            logger.info("enrichEquity exec completed for equity:{}",equity.getDefaultEquityIdentifier());
        } catch (Exception e) {

            logger.error("enrichEquity exec failed for equity:{} {}",equity.getDefaultEquityIdentifier(),e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    private void addProfitLossDetails(EquityIdentifier defaultEquityIdentifier, Equity equity, Equity equityFromDb, boolean forceUpdate) {
        try {
            boolean updateRequiredForEquityAttribute =equityFromDb==null||  isUpdateRequiredForEquityAttribute( equityFromDb.getEquityCurrentPriceStats(),forceUpdate);
            if(updateRequiredForEquityAttribute){

                profitLossAttributeService.enrichEquity(defaultEquityIdentifier,equity);
            }

        } catch (Exception e) {
            logger.error("addCurrentPriceDetails exec failed for equity:{} {}",equity.getEquityIdentifiers(),e.getMessage(),e);

        }
    }


    private void  updateEquityIdentityAndSourceDetails(EquityIdentifier defaultEquityIdentifier, Equity equity) throws ServiceException {

        ScreenerEquitySearchResponseLineItem screenerEquitySearchResponseLineItem = getScreenerEquitySearchResponseLineItem(defaultEquityIdentifier);

        if (screenerEquitySearchResponseLineItem != null) {
             addEquityIdentifier(defaultEquityIdentifier, equity, screenerEquitySearchResponseLineItem);

            addSourceDetails(equity, screenerEquitySearchResponseLineItem);
        }

    }

    @Nullable
    private ScreenerEquitySearchResponseLineItem getScreenerEquitySearchResponseLineItem(EquityIdentifier defaultEquityIdentifier) throws ServiceException {
        ScreenerEquitySearchResponseLineItem screenerEquitySearchResponseLineItem = null;

        List<ScreenerEquitySearchResponseLineItem> lineItems = screenerService.findEquity(defaultEquityIdentifier);

        if(CollectionUtils.isNotEmpty(lineItems)){
              screenerEquitySearchResponseLineItem = lineItems.get(0);


        }
        return screenerEquitySearchResponseLineItem;
    }

    protected void addSourceDetails(Equity equity, ScreenerEquitySearchResponseLineItem screenerEquitySearchResponseLineItem) {
        SourceDetails sourceDetails = new SourceDetails();
        sourceDetails.setSourceName(Constants.SOURCE_SCREENER_IO);
        sourceDetails.setEquityName(screenerEquitySearchResponseLineItem.getName());
        sourceDetails.setSourceUrl(screenerEquitySearchResponseLineItem.getUrl());
        equity.getSourceDetails().addSourceDetails(sourceDetails);
    }

    protected void addEquityIdentifier(EquityIdentifier defaultEquityIdentifier, Equity equity, ScreenerEquitySearchResponseLineItem lineItem) {
        EquityIdentifier screenerEquityIdentifier;

        screenerEquityIdentifier = new EquityIdentifier(lineItem.getName(),defaultEquityIdentifier.getExchange(), Constants.SOURCE_SCREENER_IO);
        screenerEquityIdentifier.putAdditionalAttribute("url",  lineItem.getAbsoluteUrl());
        equity.getEquityIdentifiers().getIdentifiers().add(screenerEquityIdentifier);

    }
}
