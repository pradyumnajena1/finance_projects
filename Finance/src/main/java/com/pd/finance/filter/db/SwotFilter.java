package com.pd.finance.filter.db;

import com.pd.finance.filter.EquityFilter;
import com.pd.finance.filter.FilterType;
import com.pd.finance.model.Equity;
import com.pd.finance.model.EquitySwotDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.List;

public class SwotFilter  implements EquityFilter {
    private static final Logger logger = LoggerFactory.getLogger(SwotFilter.class);


    private int minStrengths =0 ;
    private int minOpportunities = 0;
    private int maxThreats = Integer.MAX_VALUE;
    private int maxWeaknesses = Integer.MAX_VALUE;

    public int getMinStrengths() {
        return minStrengths;
    }

    public void setMinStrengths(int minStrengths) {
        this.minStrengths = minStrengths;
    }

    public int getMinOpportunities() {
        return minOpportunities;
    }

    public void setMinOpportunities(int minOpportunities) {
        this.minOpportunities = minOpportunities;
    }

    public int getMaxThreats() {
        return maxThreats;
    }

    public void setMaxThreats(int maxThreats) {
        this.maxThreats = maxThreats;
    }

    public int getMaxWeaknesses() {
        return maxWeaknesses;
    }

    public void setMaxWeaknesses(int maxWeaknesses) {
        this.maxWeaknesses = maxWeaknesses;
    }

    @Override
    public FilterType getFilterType() {

        return FilterType.InDb;
    }

    @Override
    public Criteria getCriteria(String parentObject) {

        List<Criteria> criteriaList = new ArrayList<>();
        criteriaList.add(Criteria.where("swotDetails.strengths."+(this.minStrengths-1)).exists(true));
        criteriaList.add(Criteria.where("swotDetails.opportunities."+(this.minOpportunities-1)).exists(true));
        criteriaList.add(Criteria.where("swotDetails.threats."+(this.maxThreats)).exists(false));
        criteriaList.add(Criteria.where("swotDetails.weaknesses."+(this.maxWeaknesses)).exists(false));

        Criteria criteria = new Criteria().andOperator(criteriaList.toArray( new Criteria[criteriaList.size()]));
        return criteria;

    }

    @Override
    public boolean apply(Equity equity) {
        boolean isValid = false;

        try {
            EquitySwotDetails swotDetails = equity.getSwotDetails();
            isValid = swotDetails.getStrengths().size()>=  getMinStrengths() &&
                    swotDetails.getOpportunities().size()>= getMinOpportunities() &&
                    swotDetails.getWeaknesses().size()<= getMaxWeaknesses() &&
                    swotDetails.getThreats().size()<= getMaxThreats()
            ;
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return isValid;
    }
}
