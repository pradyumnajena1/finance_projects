package com.pd.finance.service.equityenricher;

import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityIdentifier;
import com.pd.finance.service.IEquityService;
import com.pd.finance.service.equityattribute.IEquityAttributeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MoneyControlEquityEnricherService  extends AbstractEquityEnricherService implements IEquityEnricherService {

    private static final Logger logger = LoggerFactory.getLogger(MoneyControlEquityEnricherService.class);

    @Autowired
    private IEquityService equityService;

    @Resource(name = "equityBasicDetailsAttributeService")
    private IEquityAttributeService basicDetailsAttributeService;

    @Resource(name = "equityEssentialsAttributeService")
    private IEquityAttributeService equityEssentialsAttributeService;

    @Resource(name = "equityInsightsAttributeService")
    private IEquityAttributeService equityInsightsAttributeService;

    @Resource(name = "equityOverviewAttributeService")
    private IEquityAttributeService equityOverviewAttributeService;



    @Resource(name = "equityTechnicalDetailsAttributeService")
    private IEquityAttributeService equityTechnicalDetailsAttributeService;

    @Resource(name = "equitySwotAttributeService")
    private IEquityAttributeService equitySwotAttributeService;

    @Resource(name = "equityCurrentPriceStatsAttributeService")
    private IEquityAttributeService equityCurrentPriceStatsAttributeService;

    @Resource(name = "equityBrokerResearchAttributeService")
    private IEquityAttributeService equityBrokerResearchAttributeService;

    @Resource(name = "equityDealDetailsAttributeService")
    private IEquityAttributeService equityDealDetailsAttributeService;


    @Override
    public void enrichEquity(EquityIdentifier identifier, Equity equity, boolean forceUpdate) throws ServiceException {
        try {
            logger.info("enrichEquity exec started for equity:{}",equity.getDefaultEquityIdentifier());
            Equity equityFromDb = equityService.getEquity(identifier);

            addCurrentPriceDetails(identifier,equity,equityFromDb,forceUpdate);

            addBasicDetails(identifier,equity);
            addSwotDetails(identifier,equity,equityFromDb,forceUpdate);
            addEssentialDetails(identifier,equity,equityFromDb,forceUpdate);
            addEquityOverview(identifier,equity,equityFromDb,forceUpdate);
            addTechnicalDetails(identifier,equity,equityFromDb,forceUpdate);
            addEquityInsights(identifier,equity,equityFromDb,forceUpdate);
            addBrokerResearchDetails(identifier,equity,equityFromDb,forceUpdate);
            addDealDetails(identifier,equity,equityFromDb,forceUpdate);

            logger.info("enrichEquity exec completed for equity:{}",equity.getDefaultEquityIdentifier());
        } catch (Exception e) {

            logger.error("enrichEquity exec failed for equity:{} {}",equity.getDefaultEquityIdentifier(),e.getMessage(),e);
            throw new ServiceException(e);
        }

    }

    private void addDealDetails(EquityIdentifier identifier, Equity equity, Equity equityFromDb, boolean forceUpdate) {
        try {
            boolean isUpdateRequired = equityFromDb==null|| isUpdateRequiredForEquityAttribute(equityFromDb.getEquityDealsDetails(),forceUpdate);

            if(isUpdateRequired){

                equityDealDetailsAttributeService.enrichEquity(identifier,equity);
            }

        } catch (Exception e) {
            logger.error("addDealDetails exec failed for equity:{} {}",equity.getDefaultEquityIdentifier(),e.getMessage(),e);

        }
    }

    private void addBrokerResearchDetails(EquityIdentifier identifier, Equity equity, Equity equityFromDb, boolean forceUpdate) {
        try {
            boolean isUpdateRequired = equityFromDb==null|| isUpdateRequiredForEquityAttribute(equityFromDb.getBrokerResearchDetails(),forceUpdate);
           // isUpdateRequired = true;
            if(isUpdateRequired){

                equityBrokerResearchAttributeService.enrichEquity(identifier,equity);
            }

        } catch (Exception e) {
            logger.error("addBrokerResearchDetails exec failed for equity:{} {}",equity.getDefaultEquityIdentifier(),e.getMessage(),e);

        }
    }


    private void addCurrentPriceDetails(EquityIdentifier identifier, Equity equity, Equity equityFromDb, boolean forceUpdate)   {
        try {
            boolean isUpdateRequired = equityFromDb==null|| isUpdateRequiredForEquityAttribute(equityFromDb.getEquityCurrentPriceStats(),forceUpdate);
            if(isUpdateRequired){

                equityCurrentPriceStatsAttributeService.enrichEquity(identifier,equity);
            }

        } catch (Exception e) {
            logger.error("addCurrentPriceDetails exec failed for equity:{} {}",equity.getDefaultEquityIdentifier(),e.getMessage(),e);

        }
    }




    private void addBasicDetails(EquityIdentifier identifier, Equity equity)   {
        try {
            basicDetailsAttributeService.enrichEquity(identifier,equity);
        } catch (Exception e) {
            logger.error("addBasicDetails exec failed for equity:{} {}",equity.getDefaultEquityIdentifier(),e.getMessage(),e);

        }
    }

    private void addEquityInsights(EquityIdentifier identifier, Equity equity, Equity equityFromDb, boolean forceUpdate)   {
        try {
            boolean isUpdateRequired = equityFromDb==null||  isUpdateRequiredForEquityAttribute( equityFromDb.getInsights(),forceUpdate);
            if(isUpdateRequired){
                equityInsightsAttributeService.enrichEquity(identifier,equity);
            }

        } catch (Exception e) {
            logger.error("addEquityInsights exec failed for equity:{} {}",equity.getDefaultEquityIdentifier(),e.getMessage(),e);

        }
    }

    private void addTechnicalDetails(EquityIdentifier identifier, Equity equity, Equity equityFromDb, boolean forceUpdate)   {
        try {
            boolean isUpdateRequired = equityFromDb==null||  isUpdateRequiredForEquityAttribute( equityFromDb.getTechnicalDetails(),forceUpdate);
            if(isUpdateRequired){
                equityTechnicalDetailsAttributeService.enrichEquity(identifier,equity);
            }

        } catch (Exception e) {
            logger.error("addTechnicalDetails exec failed for equity:{} {}",equity.getDefaultEquityIdentifier(),e.getMessage(),e);

        }
    }

    private   void addEquityOverview(EquityIdentifier identifier, Equity equity, Equity equityFromDb, boolean forceUpdate)  {
        try {
            boolean isUpdateRequired =  equityFromDb==null|| isUpdateRequiredForEquityAttribute( equityFromDb.getOverview(),forceUpdate);
            if(isUpdateRequired){
                equityOverviewAttributeService.enrichEquity(identifier,equity);
            }

        } catch (Exception e) {
            logger.error("addEquityOverview exec failed for equity:{} {}",equity.getDefaultEquityIdentifier(),e.getMessage(),e);

        }
    }

    private   void addEssentialDetails(EquityIdentifier identifier, Equity equity, Equity equityFromDb, boolean forceUpdate)   {
        try {
            boolean isUpdateRequired = equityFromDb==null||  isUpdateRequiredForEquityAttribute( equityFromDb.getEssentials(),forceUpdate);
            if(isUpdateRequired){
                equityEssentialsAttributeService.enrichEquity(identifier,equity);
            }

        } catch (Exception e) {
            logger.error("addEssentialDetails exec failed for equity:{} {}",equity.getDefaultEquityIdentifier(),e.getMessage(),e);

        }
    }

    private   void addSwotDetails(EquityIdentifier identifier, Equity equity, Equity equityFromDb, boolean forceUpdate)   {


        try {
            boolean isUpdateRequired =  equityFromDb==null|| isUpdateRequiredForEquityAttribute( equityFromDb.getSwotDetails(),forceUpdate);
            if(isUpdateRequired){
                equitySwotAttributeService.enrichEquity(identifier,equity);
            }

        } catch (Exception e) {
            logger.error("addSwotDetails exec failed for equity:{} {}",equity.getDefaultEquityIdentifier(),e.getMessage(),e);

        }



    }

}
