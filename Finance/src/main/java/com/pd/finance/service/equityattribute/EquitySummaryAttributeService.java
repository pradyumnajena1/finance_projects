package com.pd.finance.service.equityattribute;

import com.pd.finance.converter.EquitySummaryConverter;
import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityHistoricalData;
import com.pd.finance.model.EquityHistoricalIntervalData;
import com.pd.finance.model.EquityIdentifier;
import com.pd.finance.model.equity.summary.EquitySummary;
import com.pd.finance.response.summary.EquitySummaryResponse;
import com.pd.finance.service.HistoricalDataInterval;
import com.pd.finance.service.IYahooService;
import com.pd.finance.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EquitySummaryAttributeService extends HttpGatewayEquityAttributeService {

    private static final Logger logger = LoggerFactory.getLogger(EquityHistoricalStockPriceAttributeService.class);

    @Autowired
    private IYahooService yahooService;
    private EquitySummaryConverter equitySummaryConverter = new EquitySummaryConverter();

    @Override
    public void enrichEquity(EquityIdentifier identifier, Equity equity) throws ServiceException {
        try {
            logger.info( "enrichEquity exec started for equity:{}",equity.getDefaultEquityIdentifier());
            EquitySummary equitySummary = null;
            EquitySummaryResponse equitySummaryResponse = yahooService.getEquitySummary(identifier);
            if(equitySummaryResponse!=null){

                equitySummary = equitySummaryConverter.convert(equitySummaryResponse.getEquitySummary());
                equitySummary.setSource(Constants.SOURCE_YAHOO_FINANCE);
                equitySummary.setUpdatedDate(new Date());
                equity.setEquitySummary(equitySummary);
            }

            logger.info("enrichEquity exec completed for equity:{}",equity.getDefaultEquityIdentifier());
        } catch (Exception e) {
            logger.error( "enrichEquity exec failed for equity:{}",e.getMessage(),e);
            throw new ServiceException(e);
        }
    }
}
