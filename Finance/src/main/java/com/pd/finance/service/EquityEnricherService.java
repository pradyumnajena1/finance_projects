package com.pd.finance.service;

import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.model.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

@Service
public class EquityEnricherService implements IEquityEnricherService {
    private static final Logger logger = LoggerFactory.getLogger(EquityEnricherService.class);

    @Resource(name = "equityBasicDetailsAttributeService")
    private IEquityAttributeService basicDetailsAttributeService;

    @Resource(name = "equityEssentialsAttributeService")
    private IEquityAttributeService equityEssentialsAttributeService;

    @Resource(name = "equityInsightsAttributeService")
    private IEquityAttributeService equityInsightsAttributeService;

    @Resource(name = "equityOverviewAttributeService")
    private IEquityAttributeService equityOverviewAttributeService;

    @Resource(name = "equityRecentPerformancesAttributeService")
    private IEquityAttributeService equityRecentPerformancesAttributeService;

    @Resource(name = "equityTechnicalDetailsAttributeService")
    private IEquityAttributeService equityTechnicalDetailsAttributeService;

    @Resource(name = "equitySwotAttributeService")
    private IEquityAttributeService equitySwotAttributeService;

    @Resource(name = "equityCurrentPriceStatsAttributeService")
    private IEquityAttributeService equityCurrentPriceStatsAttributeService;


    @Override
    public void enrichEquity(EquityIdentifier identifier, Equity equity) throws ServiceException{

            try {
                addCurrentPriceDetails(identifier,equity);
                addRecentPerformances(identifier,equity);
                addBasicDetails(identifier,equity);
                addSwotDetails(identifier,equity);
                addEssentialDetails(identifier,equity);
                addEquityOverview(identifier,equity);
                addTechnicalDetails(identifier,equity);
                addEquityInsights(identifier,equity);
            } catch (Exception e) {
                logger.error(e.getMessage(),e);
                throw new ServiceException(e);
            }


    }

    private void addRecentPerformances(EquityIdentifier identifier, Equity equity)  {
        try {
            equityRecentPerformancesAttributeService.enrichEquity(identifier,equity);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);

        }
    }

    private void addCurrentPriceDetails(EquityIdentifier identifier, Equity equity)   {
        try {
            equityCurrentPriceStatsAttributeService.enrichEquity(identifier,equity);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);

        }
    }

    private void addBasicDetails(EquityIdentifier identifier, Equity equity)   {
        try {
            basicDetailsAttributeService.enrichEquity(identifier,equity);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);

        }
    }

    private void addEquityInsights(EquityIdentifier identifier, Equity equity)   {
        try {
           equityInsightsAttributeService.enrichEquity(identifier,equity);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);

        }
    }

    private void addTechnicalDetails(EquityIdentifier identifier, Equity equity)   {
        try {
           equityTechnicalDetailsAttributeService.enrichEquity(identifier,equity);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);

        }
    }

    private   void addEquityOverview(EquityIdentifier identifier, Equity equity)  {
        try {
            equityOverviewAttributeService.enrichEquity(identifier,equity);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);

        }
    }

    private   void addEssentialDetails(EquityIdentifier identifier, Equity equity)   {
        try {
           equityEssentialsAttributeService.enrichEquity(identifier,equity);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);

        }
    }

    private   void addSwotDetails(EquityIdentifier identifier, Equity equity)   {


        try {
             equitySwotAttributeService.enrichEquity(identifier,equity);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);

        }



    }
}
