package com.pd.finance.filter;

import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityOverview;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class OverviewFilter  implements  EquityFilter{

    private static final Logger logger = LoggerFactory.getLogger(SwotFilter.class);


    private BigDecimal maxPE = BigDecimal.valueOf(100000);
    private BigInteger minVolume = BigInteger.ZERO;

    public BigDecimal getMaxPE() {
        return maxPE;
    }

    public void setMaxPE(BigDecimal maxPE) {
        this.maxPE = maxPE;
    }

    public BigInteger getMinVolume() {
        return minVolume;
    }

    public void setMinVolume(BigInteger minVolume) {
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

        Criteria criteria = new Criteria().andOperator(criteriaList.toArray( new Criteria[criteriaList.size()]));
        return criteria;
    }

    @Override
    public boolean apply(Equity equity) {
        boolean isValid = false;

        try {
            EquityOverview equityOverview = equity.getOverview();
            isValid =   getMaxPE().compareTo(equityOverview.getStockPE())>=0 &&
                     getMinVolume().compareTo(equityOverview.getVolume())<=0

            ;
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return isValid;
    }
}
