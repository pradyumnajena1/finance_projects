package com.pd.finance.htmlscrapper.listings;

import com.pd.finance.config.ApplicationConfig;
import com.pd.finance.model.Equity;
import com.pd.finance.service.IDocumentService;
import com.pd.finance.service.IEquityService;
import com.pd.finance.utils.Constants;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;


@Component
public class CompanyListingsFactory implements ICompanyListingsFactory {
    private Logger logger = LoggerFactory.getLogger(CompanyListingsFactory.class);

    @Autowired
    private IDocumentService documentService;

    @Autowired
    private ApplicationConfig config;

    @Autowired
    private IEquityService equityService;

    @Override
    public List<Equity> getEquities(int numEquitiesToAdd) {
        logger.info("getEquities exec started numEquitiesToAdd {}",numEquitiesToAdd);

        List<Equity> equityCollector = new ArrayList<>();
        try {
            List<String> equityNamePrefixes = getEquityNamePrefixes();
           outer: for (String prefix : equityNamePrefixes) {
                logger.info("getEquities processing equities with prefix {}",prefix);
                String listingPageUrl = MessageFormat.format(config.getEnvProperty("MCCompanyNamesUrl"), prefix);
                Document document = getListingPageDocument(listingPageUrl);
                if (document != null) {
                    Elements tds = document.select(getCssQuery());
                    if (tds != null) {

                        for(Element td:tds){
                            List<Equity> equities = extractEquities(td);
                            equityCollector.addAll(equities);
                            int numEquitiesCollected = equityCollector.size();

                            logger.info("getEquities num equities collected {}", numEquitiesCollected);
                            if(numEquitiesToAdd!=-1 && numEquitiesCollected >numEquitiesToAdd){
                                break outer;
                            }
                        }


                    }

                }

               logger.info("getEquities completed processing equities with prefix {}",prefix);
            }
        } catch (Exception exception) {
            logger.error(exception.getMessage(), exception);
        }

        return equityCollector;
    }

    @NotNull
    protected String getCssQuery() {
        return "#mc_mainWrapper > div.PA10 > div.FL > div.PT15 > table > tbody > tr  > td";
    }

    private List<Equity> extractEquities(Element td) {
        List<Equity> equitiesCollector = new ArrayList<>();
        try {
            String equityName = td.text();
            String equitySourceUrl = td.select("a").attr("href");

            extractEquity(equitiesCollector, equityName, equitySourceUrl, Constants.EXCHANGE_NSI);
            extractEquity(equitiesCollector, equityName, equitySourceUrl, Constants.EXCHANGE_BSE);
        } catch (Exception exception) {
            logger.error(exception.getMessage(), exception);
        }
        return equitiesCollector;
    }

    private void extractEquity(List<Equity> equitiesCollector, String equityName, String equitySourceUrl, String exchange) {
        try {
            Equity equity;
            equity = equityService.createEquityWithMandatoryAttributes(equityName, equitySourceUrl, exchange, Constants.SOURCE_MONEY_CONTROL);
            if (equity != null) {
                equitiesCollector.add(equity);
            }
        } catch (Exception exception) {
            logger.error(exception.getMessage(), exception);
        }
    }

    @NotNull
    protected List<String> getEquityNamePrefixes() {
        List<String> equityNamePrefixes = new ArrayList<>();
        for (char prefix = 'A'; prefix < 'Z'; prefix++) {
            equityNamePrefixes.add(String.valueOf(prefix));
        }
        equityNamePrefixes.add("others");
        return equityNamePrefixes;
    }

    private Document getListingPageDocument(String listingPageUrl) {
        Document document = null;
        try {

            document = documentService.getDocument(listingPageUrl, Period.ofDays(15));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return document;
    }


}
