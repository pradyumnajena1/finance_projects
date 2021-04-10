package com.pd.finance.converter;


import com.pd.finance.model.equity.summary.FinancialChartLineItem;
import com.pd.finance.model.equity.summary.FinancialsChart;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class FinancialChartConverter implements IConverter<com.pd.finance.response.summary.FinancialsChart, FinancialsChart>{
    private Logger logger = LoggerFactory.getLogger(FinancialChartConverter.class);

    @Override
    public FinancialsChart convert(com.pd.finance.response.summary.FinancialsChart financialsChart) {
        FinancialsChart result = null;
        try {
            if(financialsChart==null){
                return result;
            }
            result = new FinancialsChart();
            result.setQuarterly(convert(financialsChart.getQuarterly()));
            result.setYearly(convert(financialsChart.getYearly()));
        } catch (Exception exception) {
           logger.error(exception.getMessage(),exception);
        }
        return result;
    }

    private List<FinancialChartLineItem> convert(List<com.pd.finance.response.summary.FinancialChartLineItem> lineItems) {
        List<FinancialChartLineItem> result = new ArrayList<>();
        if(CollectionUtils.isEmpty(lineItems)){
            return result;
        }
        for(com.pd.finance.response.summary.FinancialChartLineItem lineItem:lineItems){
            FinancialChartLineItem financialChartLineItem = new FinancialChartLineItem();
            financialChartLineItem.setDate(String.valueOf( lineItem.getDate()));
            financialChartLineItem.setEarnings(lineItem.getEarnings().getRaw());
            financialChartLineItem.setRevenue(lineItem.getRevenue().getRaw());
            result.add(financialChartLineItem);
        }

        return result;
    }
}
