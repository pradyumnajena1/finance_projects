package com.pd.finance.filter.db;

import com.pd.finance.filter.EquityFilter;
import com.pd.finance.filter.FilterType;
import com.pd.finance.filter.db.SwotFilter;
import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityOverview;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class OverviewFilter extends AbstractDBFilter<Equity>  implements EquityFilter {

    private static final Logger logger = LoggerFactory.getLogger(SwotFilter.class);


    private BigDecimal maxPE = BigDecimal.valueOf(100000);
    private BigDecimal minMarketCap = BigDecimal.valueOf(0);
    private BigDecimal minVolume = BigDecimal.valueOf(0);



    public BigDecimal getMaxPE() {
        return maxPE;
    }

    public void setMaxPE(BigDecimal maxPE) {
        this.maxPE = maxPE;
    }

    public BigDecimal getMinMarketCap() {
        return minMarketCap;
    }

    public void setMinMarketCap(BigDecimal minMarketCap) {
        this.minMarketCap = minMarketCap;
    }

    public BigDecimal getMinVolume() {
        return minVolume;
    }

    public void setMinVolume(BigDecimal minVolume) {
        this.minVolume = minVolume;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.InDb;
    }

    @Override
    public Criteria getCriteria(String parentObject) {
        List<Criteria> criteriaList = new ArrayList<>();

        criteriaList.add(Criteria.where("overview.stockPE").lte(getMaxPE()));
        criteriaList.add(Criteria.where("overview.volume").gte(getMinVolume()));
        criteriaList.add(Criteria.where("overview.marketCap").gte(getMinMarketCap()));

         return getAsCompositeCriteria(criteriaList);
    }

    @Override
    public boolean apply(Equity equity) {
        boolean isValid = false;

        try {
            EquityOverview equityOverview = equity.getOverview();
            isValid =   getMaxPE().compareTo(equityOverview.getStockPE())>=0 &&
                        getMinVolume().compareTo(equityOverview.getVolume())<=0 &&
                        getMinMarketCap().compareTo(equityOverview.getMarketCap())<=0;

            ;
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return isValid;
    }
}
