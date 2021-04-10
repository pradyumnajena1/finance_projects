package com.pd.finance.converter;

import com.pd.finance.controller.WebCrawlerController;
import com.pd.finance.model.equity.summary.FinancialData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FinancialDataConverter implements IConverter<com.pd.finance.response.summary.FinancialData, FinancialData> {
    private Logger logger = LoggerFactory.getLogger(FinancialDataConverter.class);

    @Override
    public FinancialData convert(com.pd.finance.response.summary.FinancialData financialData) {
        FinancialData result = null;
        try {
            if(financialData==null){
                return null;
            }
            result=new FinancialData();
            result.setCurrentPrice(financialData.getCurrentPrice().getRaw());
            result.setFinancialCurrency(financialData.getFinancialCurrency());
            result.setMaxAge(financialData.getMaxAge());

            setRecomendationAttributes(financialData, result);
            setRevenueAttributes(financialData, result);
            setEbitdaAttributes(financialData, result);
            setGrossAttributes(financialData, result);
            setTargetAttributes(financialData, result);
            setTotalAttributes(financialData, result);
        } catch (Exception exception) {
            logger.error(exception.getMessage(),exception);
        }

        return result;
    }

    protected void setRecomendationAttributes(com.pd.finance.response.summary.FinancialData financialData, FinancialData result) {
        result.setRecommendationKey(financialData.getRecommendationKey());
        result.setRecommendationMean(financialData.getRecommendationMean().getRaw());
    }

    protected void setRevenueAttributes(com.pd.finance.response.summary.FinancialData financialData, FinancialData result) {
        result.setRevenueGrowth(financialData.getRevenueGrowth().getRaw());
        result.setRevenuePerShare(financialData.getRevenuePerShare().getRaw());
        result.setTotalRevenue(financialData.getTotalRevenue().getRaw());
    }

    protected void setGrossAttributes(com.pd.finance.response.summary.FinancialData financialData, FinancialData result) {
        result.setGrossMargins(financialData.getGrossMargins().getRaw());
        result.setGrossProfits(financialData.getGrossProfits().getRaw());
    }

    protected void setEbitdaAttributes(com.pd.finance.response.summary.FinancialData financialData, FinancialData result) {
        result.setDebtToEquity(financialData.getDebtToEquity().getRaw());
        result.setEbitda(financialData.getEbitda().getRaw());
        result.setEbitdaMargins(financialData.getEbitdaMargins().getRaw());
    }

    protected void setTotalAttributes(com.pd.finance.response.summary.FinancialData financialData, FinancialData result) {
        result.setTotalRevenue(financialData.getTotalRevenue().getRaw());
        result.setTotalCash(financialData.getTotalCash().getRaw());
        result.setTotalCashPerShare(financialData.getTotalCashPerShare().getRaw());
        result.setTotalDebt(financialData.getTotalDebt().getRaw());

    }

    protected void setTargetAttributes(com.pd.finance.response.summary.FinancialData financialData, FinancialData result) {
        result.setTargetHighPrice(financialData.getTargetHighPrice().getRaw());
        result.setTargetLowPrice(financialData.getTargetLowPrice().getRaw());
        result.setTargetMeanPrice(financialData.getTargetMeanPrice().getRaw());
        result.setTargetMedianPrice(financialData.getTargetMedianPrice().getRaw());

    }
}
