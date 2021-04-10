package com.pd.finance.converter;

import com.pd.finance.model.equity.summary.FinancialData;
import com.pd.finance.model.equity.summary.Trend;
import com.pd.finance.response.summary.EarningsChart;
import com.pd.finance.response.summary.EarningsChartLineItem;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class EarningsChartConverter implements IConverter<com.pd.finance.response.summary.EarningsChart, com.pd.finance.model.equity.summary.EarningsChart>{
    private static final Logger logger = LoggerFactory.getLogger(EarningsChartConverter.class);


    @Override
    public com.pd.finance.model.equity.summary.EarningsChart convert(EarningsChart earningsChart) {
        com.pd.finance.model.equity.summary.EarningsChart result = null;
        try {
            if(earningsChart==null){
                return null;
            }
            result=new com.pd.finance.model.equity.summary.EarningsChart();

            result.setQuarterly(convert(earningsChart.getQuarterly()));


        } catch (Exception exception) {
            logger.error(exception.getMessage(),exception);
        }

        return result;
    }

    private List<com.pd.finance.model.equity.summary.EarningsChartLineItem> convert(List<EarningsChartLineItem> earningsChartLineItems) {
        List<com.pd.finance.model.equity.summary.EarningsChartLineItem> result = new ArrayList<>();
        if(CollectionUtils.isEmpty(earningsChartLineItems)){
            return result;
        }
        for(com.pd.finance.response.summary.EarningsChartLineItem lineItem  :earningsChartLineItems){
            if(lineItem==null){
                result.add(null);
            }else {
                com.pd.finance.model.equity.summary.EarningsChartLineItem earningsChartLineItem = new com.pd.finance.model.equity.summary.EarningsChartLineItem();
                earningsChartLineItem.setActual(lineItem.getActual().getRaw());
                earningsChartLineItem.setDate(lineItem.getDate());
                earningsChartLineItem.setEstimate(lineItem.getEstimate().getRaw());

                result.add(earningsChartLineItem);
            }

        }
        return result;
    }
}
