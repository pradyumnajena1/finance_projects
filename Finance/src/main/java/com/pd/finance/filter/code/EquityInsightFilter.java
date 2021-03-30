package com.pd.finance.filter.code;

import com.pd.finance.filter.EquityFilter;
import com.pd.finance.filter.FilterType;
import com.pd.finance.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;

public class EquityInsightFilter  implements EquityFilter {
    private static final Logger logger = LoggerFactory.getLogger(EquityInsightFilter.class);


    private InsightsFilter shareholdingPatternsFilter;
    private InsightsFilter industryComparisionFilter;
    private InsightsFilter priceInsightFilter;
    private FinancialInsightFilter financialInsightFilter;




    public InsightsFilter getShareholdingPatternsFilter() {
        return shareholdingPatternsFilter;
    }

    public void setShareholdingPatternsFilter(InsightsFilter shareholdingPatternsFilter) {
        this.shareholdingPatternsFilter = shareholdingPatternsFilter;
    }

    public InsightsFilter getIndustryComparisionFilter() {
        return industryComparisionFilter;
    }

    public void setIndustryComparisionFilter(InsightsFilter industryComparisionFilter) {
        this.industryComparisionFilter = industryComparisionFilter;
    }

    public InsightsFilter getPriceInsightFilter() {
        return priceInsightFilter;
    }

    public void setPriceInsightFilter(InsightsFilter priceInsightFilter) {
        this.priceInsightFilter = priceInsightFilter;
    }

    public FinancialInsightFilter getFinancialInsightFilter() {
        return financialInsightFilter;
    }

    public void setFinancialInsightFilter(FinancialInsightFilter financialInsightFilter) {
        this.financialInsightFilter = financialInsightFilter;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.InCode;
    }

    @Override
    public Criteria getCriteria(String pa) {
        return null;
    }

    @Override
    public boolean apply(Equity equity) {
        EquityInsights insights = equity.getInsights();
        if(insights==null){
            return false;
        }
        if(getFinancialInsightFilter()!=null){

            if(!getFinancialInsightFilter().apply(insights.getFinancialInsights())){
                logger.info("isValidEquity FinancialInsightFilter didn't matched for equity {}",equity.getDefaultEquityIdentifier());
                return false;
            }
        }
        if(getPriceInsightFilter()!=null){
            boolean result = false;
            PriceInsights priceInsights = insights.getPriceInsights();

            if(priceInsights != null) {
                result = getPriceInsightFilter().apply(priceInsights.getLineItems());
            }

            if(!result){
                logger.info("isValidEquity PriceInsightFilter didn't matched for equity {}",equity.getDefaultEquityIdentifier());
                return false;
            }
        }

       if(getIndustryComparisionFilter()!=null){
           boolean result = false;
           IndustryComparisionInsights industryComparisionInsights = insights.getIndustryComparisionInsights();

           if(industryComparisionInsights != null) {
               result = getIndustryComparisionFilter().apply(industryComparisionInsights.getLineItems());
           }


           if(!result){
               logger.info("isValidEquity IndustryComparisionFilter didn't matched for equity {}",equity.getDefaultEquityIdentifier());
               return false;
           }
       }
        if(getShareholdingPatternsFilter()!=null){
            boolean result = false;
            ShareholdingPatternInsights shareholdingPatternInsights = insights.getShareholdingPatternInsights();

            if(shareholdingPatternInsights != null) {
                result = getShareholdingPatternsFilter().apply(shareholdingPatternInsights.getLineItems());
            }


            if(!result){
                logger.info("isValidEquity ShareholdingPatternsFilter didn't matched for equity {}",equity.getDefaultEquityIdentifier());
                return false;
            }
        }

        return true;
    }


}
