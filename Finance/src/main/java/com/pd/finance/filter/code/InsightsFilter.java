package com.pd.finance.filter.code;

import com.pd.finance.filter.EquityFilter;
import com.pd.finance.filter.FilterType;
import com.pd.finance.filter.IFilter;
import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityInsightLineItem;
import com.pd.finance.model.StockMarketSentiments;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

public class InsightsFilter  implements IFilter<List<EquityInsightLineItem>> {

    private Integer minBullish;
    private Integer maxBearish;

    public Integer getMinBullish() {
        return minBullish;
    }

    public void setMinBullish(Integer minBullish) {
        this.minBullish = minBullish;
    }

    public Integer getMaxBearish() {
        return maxBearish;
    }

    public void setMaxBearish(Integer maxBearish) {
        this.maxBearish = maxBearish;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.InCode;
    }

    @Override
    public Criteria getCriteria(String parentObject) {
        throw new UnsupportedOperationException()  ;
    }

    @Override
    public boolean apply(List<EquityInsightLineItem> lineItems) {

            if(lineItems==null || lineItems.size()==0){
                return false;
            }

            Integer maxBearish =  getMaxBearish();
            Integer minBullish =  getMinBullish();


            if (!isValid(maxBearish, minBullish, lineItems)) {
                return false;
            }

           return true;
    }
    private boolean isValid(Integer maxBearish, Integer minBullish, List<EquityInsightLineItem> lineItems) {
        if( (maxBearish!=null || minBullish!=null) && (lineItems ==null)){
            return false;
        }
        if(maxBearish !=null){
            long count = lineItems.stream().filter(lineItem -> lineItem.getMarketSentiments() == StockMarketSentiments.Bearish).count();
            if(count >maxBearish){
                return false;
            }
        }
        if(minBullish!=null ){
            long count = lineItems.stream().filter(lineItem -> lineItem.getMarketSentiments() == StockMarketSentiments.Bullish).count();
            if(count <minBullish){
                return false;
            }
        }
        return true;
    }


}
