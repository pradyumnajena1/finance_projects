package com.pd.finance.service.equityenricher;

import com.pd.finance.config.ApplicationConfig;
import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityHistoricalData;
import com.pd.finance.model.EquityIdentifier;
import com.pd.finance.model.SourceDetails;
import com.pd.finance.response.EquityStockExchangeDetailsResponse;
import com.pd.finance.service.IEquityService;
import com.pd.finance.service.equityattribute.IEquityAttributeService;
import com.pd.finance.utils.Constants;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;

@Service
public class YahooEquityEnricherService extends AbstractEquityEnricherService implements IEquityEnricherService {

    private static final Logger logger = LoggerFactory.getLogger(YahooEquityEnricherService.class);


    @Autowired
    private ApplicationConfig config;
    @Resource(name = "equityStockExchangeDetailsAttributeService")
    private IEquityAttributeService equityStockExchangeDetailsAttributeService;

    @Resource(name = "equityHistoricalStockPriceAttributeService")
    private IEquityAttributeService historicalStockPriceAttributeService;

    @Resource(name = "equityPerformancesAttributeService")
    private IEquityAttributeService equityPerformancesAttributeService;

    @Resource(name = "equitySummaryAttributeService")
    private IEquityAttributeService equitySummaryAttributeService;

    @Autowired
    private IEquityService equityService;

    @Override
    public void enrichEquity(EquityIdentifier defaultEquityIdentifier, Equity equity, boolean forceUpdate) throws ServiceException {
        try {
            logger.info("enrichEquity exec started for equity:{}",equity.getDefaultEquityIdentifier());
            Equity equityFromDb = equityService.getEquity(defaultEquityIdentifier);

            addEquityStockExchangeDetails(defaultEquityIdentifier,equity,equityFromDb,forceUpdate);
            updateEquityIdentityAndSourceDetails(defaultEquityIdentifier, equity);

            addHistoricalStockPrice(defaultEquityIdentifier,equity,equityFromDb,forceUpdate);

            addRecentPerformances(defaultEquityIdentifier,equity,equityFromDb,forceUpdate);

            addSummary(defaultEquityIdentifier,equity,equityFromDb,forceUpdate);

            logger.info("enrichEquity exec completed for equity:{}",equity.getDefaultEquityIdentifier());
        } catch (Exception e) {

            logger.error("enrichEquity exec failed for equity:{} {}",equity.getDefaultEquityIdentifier(),e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    private void addSummary(EquityIdentifier identifier, Equity equity, Equity equityFromDb, boolean forceUpdate) {
        try {
            boolean isUpdateRequired =  equityFromDb == null || isUpdateRequiredForEquityAttribute(equityFromDb.getPerformances(),forceUpdate);

            if(isUpdateRequired){
                equitySummaryAttributeService.enrichEquity(identifier,equity);
            }

        } catch (Exception e) {
            logger.error("addRecentPerformances exec failed for equity:{} {}",equity.getDefaultEquityIdentifier(),e.getMessage(),e);

        }
    }

    private void addRecentPerformances(EquityIdentifier identifier, Equity equity, Equity equityFromDb, boolean forceUpdate)  {
        try {
            boolean isUpdateRequired =  equityFromDb == null || isUpdateRequiredForEquityAttribute(equityFromDb.getPerformances(),forceUpdate);

            if(isUpdateRequired){
                equityPerformancesAttributeService.enrichEquity(identifier,equity);
            }

        } catch (Exception e) {
            logger.error("addRecentPerformances exec failed for equity:{} {}",equity.getDefaultEquityIdentifier(),e.getMessage(),e);

        }
    }
    private void addHistoricalStockPrice(EquityIdentifier identifier, Equity equity, Equity equityFromDb, boolean forceUpdate) {
        try {
            if(equityFromDb==null || isUpdateRequiredForHistoricalDate(equityFromDb,forceUpdate)) {
                historicalStockPriceAttributeService.enrichEquity(identifier, equity);
            }
        } catch (Exception e) {
            logger.error("addEquityStockExchangeDetails exec failed for equity:{} {}",equity.getDefaultEquityIdentifier(),e.getMessage(),e);

        }
    }

    private boolean isUpdateRequiredForHistoricalDate(Equity equityFromDb, boolean forceUpdate) {
        EquityHistoricalData historicalData = equityFromDb.getHistoricalData();
        if(historicalData==null){
            return true;
        }
        if(isUpdateRequiredForEquityAttribute(historicalData.getDailyHistoricalData(),forceUpdate)){
            return true;
        }
        if(isUpdateRequiredForEquityAttribute(historicalData.getWeeklyHistoricalIntervalData(),forceUpdate)){
            return true;
        }
        if(isUpdateRequiredForEquityAttribute(historicalData.getMonthlyEquityHistoricalIntervalData(),forceUpdate)){
            return true;
        }
        return false;
    }

    private void addEquityStockExchangeDetails(EquityIdentifier identifier, Equity equity, Equity equityFromDb, boolean forceUpdate) {
        try {
            if(equityFromDb==null || isUpdateRequiredForEquityAttribute(equityFromDb.getStockExchangeDetails())) {
                equityStockExchangeDetailsAttributeService.enrichEquity(identifier, equity);
            }
        } catch (Exception e) {
            logger.error("addEquityStockExchangeDetails exec failed for equity:{} {}",equity.getDefaultEquityIdentifier(),e.getMessage(),e);

        }
    }
    private void  updateEquityIdentityAndSourceDetails(EquityIdentifier defaultEquityIdentifier, Equity equity) throws ServiceException {

        EquityStockExchangeDetailsResponse stockExchangeDetails = equity.getStockExchangeDetails();
        if(stockExchangeDetails!=null){
            addEquityIdentifier(defaultEquityIdentifier, equity );

            addSourceDetails(defaultEquityIdentifier,equity );
        }



    }
    protected void addSourceDetails(EquityIdentifier defaultEquityIdentifier, Equity equity) {
        SourceDetails sourceDetails = new SourceDetails();
        sourceDetails.setSourceName(Constants.SOURCE_YAHOO_FINANCE);
        sourceDetails.setEquityName(equity.getStockExchangeDetails().getSymbol());
        sourceDetails.setSourceUrl(getYahooEquityPageUrl(equity));
        equity.getSourceDetails().addSourceDetails(sourceDetails);
    }

    @NotNull
    private String getYahooEquityPageUrl(Equity equity) {
        return MessageFormat.format(config.getEnvProperty("YahooEquityPageUrl"), equity.getStockExchangeDetails().getSymbol());
    }

    protected void addEquityIdentifier(EquityIdentifier defaultEquityIdentifier, Equity equity) {
        EquityIdentifier yahooEquityIdentifier;

        yahooEquityIdentifier = new EquityIdentifier(equity.getStockExchangeDetails().getSymbol(),defaultEquityIdentifier.getExchange(), Constants.SOURCE_YAHOO_FINANCE);
        yahooEquityIdentifier.putAdditionalAttribute("url", getYahooEquityPageUrl(equity));
        equity.getEquityIdentifiers().getIdentifiers().add(yahooEquityIdentifier);

    }
}
