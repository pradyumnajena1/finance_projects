package com.pd.finance.htmlscrapper.marketgainer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pd.finance.config.ApplicationConfig;
import com.pd.finance.htmlscrapper.equity.*;
import com.pd.finance.model.*;
import com.pd.finance.service.ICacheService;
import com.pd.finance.service.IDocumentService;
import com.pd.finance.service.IEquityService;
import com.pd.finance.service.IYahooService;
import com.pd.finance.utils.CommonUtils;
import com.pd.finance.utils.Constants;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MarketGainerEquityFactory implements IMarketGainerEquityFactory {
    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(MarketGainerEquityFactory.class);

    @Autowired
    private ApplicationConfig config;

    @Autowired
    private IEquityOverviewFactory overviewFactory;
    @Autowired
    private IEquityEssentialsFactory essentialsFactory;
    @Autowired
    private IEquitySwotFactory swotFactory;
    @Autowired
    private IEquityTechnicalDetailsFactory technicalDetailsFactory;

    @Autowired
    private IEquityInsightsFactory insightsFactory;
    @Autowired
    private IDocumentService documentService;
    @Autowired
    private ICacheService cacheService;
    @Autowired
    private IYahooService stockExchangeService;
    @Autowired
    private IEquityService equityService;


    @Override
    public List<Equity> fetchMarketGainerEquities(Document document, int maxEquitiesToFetch, String exchange) throws IOException {
        Queue<Equity> equityCollector = new ConcurrentLinkedQueue<>();
        Node histTable = MarketGainerPageHelper.extractMarketGainersTableNode(document, exchange);
        List<Node> rows = MarketGainerPageHelper.extractMarketGainerRows(histTable);
        logger.info("fetchMarketGainerEquities found {} rows ", rows.size());
        if (maxEquitiesToFetch != CommonUtils.FetchAllEquities) {
            rows = rows.subList(0, Math.max(0, Math.min(rows.size(), maxEquitiesToFetch)));
        }
        final int numEquitiesToCreate = rows.size();
        final AtomicInteger numEquitiesCreated = new AtomicInteger(0);

        rows.stream().forEach(rowNode -> {

            try {
                String equityName = MarketGainerPageHelper.extractName(rowNode);
                String equitySourceUrl = MarketGainerPageHelper.extractUrl(rowNode);
                Equity equity = equityService.createEquityWithMandatoryAttributes(equityName, equitySourceUrl, exchange, Constants.SOURCE_MONEY_CONTROL);

                if (equity != null) {
                    equityCollector.add(equity);
                }

                delay();
                int equitiesCreated = numEquitiesCreated.incrementAndGet();
                logger.info("{} equities created out of {} ", equitiesCreated, numEquitiesToCreate);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        });


        return new ArrayList<>(equityCollector);
    }

    protected void delay()  {
        try {
            Thread.sleep(Integer.parseInt(config.getEnvProperty("GainerPageCrawlerDelayInMillis")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
