package com.pd.finance.converter;

import com.pd.finance.controller.WebCrawlerController;
import com.pd.finance.model.equity.summary.DefaultKeyStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultKeyStatisticsConverter implements IConverter<com.pd.finance.response.summary.DefaultKeyStatistics, DefaultKeyStatistics> {
    private Logger logger = LoggerFactory.getLogger(DefaultKeyStatisticsConverter.class);

    @Override
    public DefaultKeyStatistics convert(com.pd.finance.response.summary.DefaultKeyStatistics defaultKeyStatistics) {
        DefaultKeyStatistics result = null;
        try {
            if(defaultKeyStatistics==null){
                return null;
            }
            result=new DefaultKeyStatistics();
            result.setBookValue(defaultKeyStatistics.getBookValue().getRaw());
            result.setEnterpriseToEbitda(defaultKeyStatistics.getEnterpriseToEbitda().getRaw());
            result.setEnterpriseToRevenue(defaultKeyStatistics.getEnterpriseToRevenue().getRaw());
            result.setFiftyTwoWeekChange(defaultKeyStatistics.get52WeekChange().getRaw());
            result.setFloatShares(defaultKeyStatistics.getFloatShares().getRaw());
            result.setForwardEps(defaultKeyStatistics.getForwardEps().getRaw());
           // result.setForwardPE(defaultKeyStatistics.getForwardPE(). );
            result.setHeldPercentInsiders(defaultKeyStatistics.getHeldPercentInsiders().getRaw());
            result.setHeldPercentInstitutions(defaultKeyStatistics.getHeldPercentInstitutions().getRaw());
            result.setMaxAge(defaultKeyStatistics.getMaxAge() );
            result.setPriceHint(defaultKeyStatistics.getPriceHint().getRaw());
            result.setProfitMargins(defaultKeyStatistics.getProfitMargins().getRaw());
            result.setSandP52WeekChange(defaultKeyStatistics.getSandP52WeekChange().getRaw());
            result.setSharesOutstanding(defaultKeyStatistics.getSharesOutstanding().getRaw());
            result.setTrailingEps(defaultKeyStatistics.getTrailingEps().getRaw());

        } catch (Exception exception) {
           logger.error(exception.getMessage(),exception);
        }

        return result;
    }
}
