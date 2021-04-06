package com.pd.finance.service.equityattribute;

import com.pd.finance.config.ApplicationConfig;
import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.htmlscrapper.marketgainer.IEquityCurrentPriceStatsFactory;
import com.pd.finance.htmlscrapper.marketgainer.MarketGainerPageHelper;
import com.pd.finance.model.*;
import com.pd.finance.response.chart.ChartResponse;
import com.pd.finance.service.IDocumentService;
import com.pd.finance.service.IHistoricalStockPriceService;
import com.pd.finance.service.IYahooService;
import com.pd.finance.utils.Constants;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class EquityCurrentPriceStatsAttributeService extends HttpGatewayEquityAttributeService {
    private static final Logger logger = LoggerFactory.getLogger(EquityCurrentPriceStatsAttributeService.class);

    @Autowired
    private IYahooService yahooService;


    @Override
    public void enrichEquity(EquityIdentifier identifier, Equity equity) throws ServiceException {
        logger.info( "enrichEquity started for equity: "+ equity.getDefaultEquityIdentifier());
        try {
            ChartResponse chartResponse = yahooService.getEquityChart(identifier );
            EquityCurrentPriceStats currentPriceStats = new EquityCurrentPriceStats();


            currentPriceStats.setLow( chartResponse.getLow());
            currentPriceStats.setHigh(chartResponse.getHigh());
            currentPriceStats.setLastPrice(chartResponse.getLastPrice());
            currentPriceStats.setPrevClose(chartResponse.getPrevClose());
            currentPriceStats.setChange(chartResponse.getChange());
            currentPriceStats.setPercentageGain(chartResponse.getPercentageGain());
            currentPriceStats.setSource(Constants.SOURCE_YAHOO_FINANCE);
            currentPriceStats.setUpdatedDate(new Date());

            equity.setEquityCurrentPriceStats(currentPriceStats);


            logger.info( "enrichEquity completed for equity: "+ equity.getDefaultEquityIdentifier());
        } catch (Exception e) {
           logger.error(e.getMessage(),e);
        }
    }



}
