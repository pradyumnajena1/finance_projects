package com.pd.finance.converter;

import com.pd.finance.controller.WebCrawlerController;
import com.pd.finance.model.equity.summary.Earnings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EarningsConverter implements IConverter<com.pd.finance.response.summary.Earnings, Earnings>{
    private Logger logger = LoggerFactory.getLogger(EarningsConverter.class);
    private EarningsChartConverter earningsChartConverter = new EarningsChartConverter();
    private FinancialChartConverter financialChartConverter = new FinancialChartConverter();
    @Override
    public Earnings convert(com.pd.finance.response.summary.Earnings earnings) {
        Earnings result = null;
        if(earnings==null){
            return null;
        }
        result=new Earnings();
        result.setEarningsChart(earningsChartConverter.convert(earnings.getEarningsChart()));
        result.setFinancialsChart(financialChartConverter.convert( earnings.getFinancialsChart()));
        result.setFinancialCurrency(earnings.getFinancialCurrency());
        result.setMaxAge(earnings.getMaxAge());
        return result;
    }
}
