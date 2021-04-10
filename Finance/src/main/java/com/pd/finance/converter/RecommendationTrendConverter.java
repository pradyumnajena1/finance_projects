package com.pd.finance.converter;

import com.pd.finance.controller.WebCrawlerController;
import com.pd.finance.model.equity.summary.RecommendationTrend;
import com.pd.finance.model.equity.summary.Trend;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class RecommendationTrendConverter implements IConverter<com.pd.finance.response.summary.RecommendationTrend, RecommendationTrend>{
    private Logger logger = LoggerFactory.getLogger(RecommendationTrendConverter.class);

    @Override
    public RecommendationTrend convert(com.pd.finance.response.summary.RecommendationTrend recommendationTrend) {
        RecommendationTrend result = null;
        try {
            if(recommendationTrend==null){
                return null;
            }
            result=new RecommendationTrend();
            result.setMaxAge(recommendationTrend.getMaxAge());
            result.setTrend(convert(recommendationTrend.getTrend()));
        } catch (Exception exception) {
            logger.error(exception.getMessage(),exception);
        }
        return result;
    }
    private List<Trend> convert(List<com.pd.finance.response.summary.Trend> trends) {
        List<Trend> result = new ArrayList<>();
        if(CollectionUtils.isEmpty(trends)){
            return result;
        }
        for(com.pd.finance.response.summary.Trend aTrend:trends){
            if(aTrend==null){
                result.add(null);
            }else {
                Trend newTrend = new Trend();
                newTrend.setBuy(aTrend.getBuy());
                newTrend.setHold(aTrend.getHold());
                newTrend.setPeriod(aTrend.getPeriod());
                newTrend.setSell(aTrend.getSell());
                newTrend.setStrongBuy(aTrend.getStrongBuy());
                newTrend.setStrongSell(aTrend.getStrongSell());
                result.add(newTrend);
            }

        }
        return result;
    }

}
