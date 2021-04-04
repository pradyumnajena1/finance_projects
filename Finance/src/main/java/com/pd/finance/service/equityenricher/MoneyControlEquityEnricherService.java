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


    @Override
    public void enrichEquity(EquityIdentifier identifier, Equity equity) throws ServiceException {
        try {
            logger.info("enrichEquity exec started for equity:{}",equity.getEquityIdentifiers());
            Equity equityFromDb = equityService.getEquity(identifier);

            addCurrentPriceDetails(identifier,equity,equityFromDb);

            addBasicDetails(identifier,equity);
            addSwotDetails(identifier,equity,equityFromDb);
            addEssentialDetails(identifier,equity,equityFromDb);
            addEquityOverview(identifier,equity,equityFromDb);
            addTechnicalDetails(identifier,equity,equityFromDb);
            addEquityInsights(identifier,equity,equityFromDb);

            logger.info("enrichEquity exec completed for equity:{}",equity.getEquityIdentifiers());
        } catch (Exception e) {

            logger.error("enrichEquity exec failed for equity:{} {}",equity.getEquityIdentifiers(),e.getMessage(),e);
            throw new ServiceException(e);
        }

    }



    private void addCurrentPriceDetails(EquityIdentifier identifier, Equity equity, Equity equityFromDb)   {
        try {
            if(equityFromDb==null || isUpdateRequiredForEquityAttribute(equityFromDb.getEquityCurrentPriceStats())){

                equityCurrentPriceStatsAttributeService.enrichEquity(identifier,equity);
            }

        } catch (Exception e) {
            logger.error("addCurrentPriceDetails exec failed for equity:{} {}",equity.getEquityIdentifiers(),e.getMessage(),e);

        }
    }


    private void addBasicDetails(EquityIdentifier identifier, Equity equity)   {
        try {
            basicDetailsAttributeService.enrichEquity(identifier,equity);
        } catch (Exception e) {
            logger.error("addBasicDetails exec failed for equity:{} {}",equity.getEquityIdentifiers(),e.getMessage(),e);

        }
    }

    private void addEquityInsights(EquityIdentifier identifier, Equity equity, Equity equityFromDb)   {
        try {
            if(equityFromDb==null || isUpdateRequiredForEquityAttribute(equityFromDb.getInsights())){
                equityInsightsAttributeService.enrichEquity(identifier,equity);
            }

        } catch (Exception e) {
            logger.error("addEquityInsights exec failed for equity:{} {}",equity.getEquityIdentifiers(),e.getMessage(),e);

        }
    }

    private void addTechnicalDetails(EquityIdentifier identifier, Equity equity, Equity equityFromDb)   {
        try {
            if(equityFromDb==null || isUpdateRequiredForEquityAttribute(equityFromDb.getTechnicalDetails())){
                equityTechnicalDetailsAttributeService.enrichEquity(identifier,equity);
            }

        } catch (Exception e) {
            logger.error("addTechnicalDetails exec failed for equity:{} {}",equity.getEquityIdentifiers(),e.getMessage(),e);

        }
    }

    private   void addEquityOverview(EquityIdentifier identifier, Equity equity, Equity equityFromDb)  {
        try {
            if(equityFromDb==null || isUpdateRequiredForEquityAttribute(equityFromDb.getOverview())){
                equityOverviewAttributeService.enrichEquity(identifier,equity);
            }

        } catch (Exception e) {
            logger.error("addEquityOverview exec failed for equity:{} {}",equity.getEquityIdentifiers(),e.getMessage(),e);

        }
    }

    private   void addEssentialDetails(EquityIdentifier identifier, Equity equity, Equity equityFromDb)   {
        try {
            if(equityFromDb==null || isUpdateRequiredForEquityAttribute(equityFromDb.getEssentials())){
                equityEssentialsAttributeService.enrichEquity(identifier,equity);
            }

        } catch (Exception e) {
            logger.error("addEssentialDetails exec failed for equity:{} {}",equity.getEquityIdentifiers(),e.getMessage(),e);

        }
    }

    private   void addSwotDetails(EquityIdentifier identifier, Equity equity, Equity equityFromDb)   {


        try {
            if(equityFromDb==null || isUpdateRequiredForEquityAttribute(equityFromDb.getSwotDetails())){
                equitySwotAttributeService.enrichEquity(identifier,equity);
            }

        } catch (Exception e) {
            logger.error("addSwotDetails exec failed for equity:{} {}",equity.getEquityIdentifiers(),e.getMessage(),e);

        }



    }

}
