package com.pd.finance.converter;

import com.pd.finance.response.summary.EquitySummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EquitySummaryConverter implements IConverter<EquitySummary, com.pd.finance.model.equity.summary.EquitySummary> {
    private static final Logger logger = LoggerFactory.getLogger(EquitySummaryConverter.class);

    private SummaryProfileConverter summaryProfileConverter = new SummaryProfileConverter();
    private RecommendationTrendConverter recommendationTrendConverter=new RecommendationTrendConverter();;
    private FinancialDataConverter financialDataConverter = new FinancialDataConverter();
    private EarningsConverter earningsConverter = new EarningsConverter();
    private DefaultKeyStatisticsConverter defaultKeyStatisticsConverter = new DefaultKeyStatisticsConverter();

    @Override
    public com.pd.finance.model.equity.summary.EquitySummary convert(EquitySummary equitySummary) {
        com.pd.finance.model.equity.summary.EquitySummary result = null;
        if(equitySummary==null){
            return null;
        }
        try {
            result = new com.pd.finance.model.equity.summary.EquitySummary();

            result.setDefaultKeyStatistics(defaultKeyStatisticsConverter.convert(equitySummary.getDefaultKeyStatistics()));
            result.setEarnings(earningsConverter.convert(equitySummary.getEarnings()));
            result.setFinancialData(financialDataConverter.convert(equitySummary.getFinancialData()));
            result.setRecommendationTrend(recommendationTrendConverter.convert(equitySummary.getRecommendationTrend()));
            result.setSummaryProfile(summaryProfileConverter.convert(equitySummary.getSummaryProfile()));

        } catch (Exception exception) {
          logger.error(exception.getMessage());
        }
        return result;
    }


}
