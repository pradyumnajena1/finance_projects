package com.pd.finance.service.equityenricher;

import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class EquityEnricherService implements IEquityEnricherService {
    private static final Logger logger = LoggerFactory.getLogger(EquityEnricherService.class);


    @Resource(name = "yahooEquityEnricherService")
    private IEquityEnricherService yahooEquityEnricherService;

    @Resource(name = "moneyControlEquityEnricherService")
    private IEquityEnricherService mcEquityEnricherService;

    @Resource(name = "screenerEquityEnricherService")
    private IEquityEnricherService screenerEquityEnricherService;


    @Override
    public void enrichEquity(EquityIdentifier identifier, Equity equity) throws ServiceException{

            try {
                logger.info("enrichEquity exec started for equity:{}",equity.getDefaultEquityIdentifier());

                mcEquityEnricherService.enrichEquity(identifier,equity);
                yahooEquityEnricherService.enrichEquity(identifier,equity);
                screenerEquityEnricherService.enrichEquity(identifier,equity);

                logger.info("enrichEquity exec completed for equity:{}",equity.getDefaultEquityIdentifier());
            } catch (Exception e) {

                logger.error("enrichEquity exec failed for equity:{} {}",equity.getDefaultEquityIdentifier(),e.getMessage(),e);
                throw new ServiceException(e);
            }


    }



}
