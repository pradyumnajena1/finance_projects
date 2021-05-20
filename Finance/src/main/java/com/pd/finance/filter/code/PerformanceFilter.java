package com.pd.finance.filter.code;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pd.finance.filter.EquityFilter;
import com.pd.finance.filter.FilterType;
import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityPerformance;
import com.pd.finance.model.EquityPerformances;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PerformanceFilter  implements EquityFilter {
    private static final Logger logger = LoggerFactory.getLogger(PerformanceFilter.class);


    private BigDecimal minimumGainPerSession = new BigDecimal("0.0");
    private int minimumGainSessions = 5;

    public BigDecimal getMinimumGainPerSession() {
        return minimumGainPerSession;
    }

    public void setMinimumGainPerSession(BigDecimal minimumGainPerSession) {
        this.minimumGainPerSession = minimumGainPerSession;
    }

    public int getMinimumGainSessions() {
        return minimumGainSessions;
    }

    public void setMinimumGainSessions(int minimumGainSessions) {
        this.minimumGainSessions = minimumGainSessions;
    }

    @Override
    @JsonIgnore
    public FilterType getFilterType() {
        return FilterType.Partial;
    }

    @Override
    public Criteria getCriteria(String parentObject) {

        List<Criteria> criteriaList = new ArrayList<>();

        criteriaList.add(Criteria.where("performances.sortedGainPercentages."+(this.minimumGainSessions-1)).exists(true));
        criteriaList.add(Criteria.where("performances.sortedGainPercentages."+(this.minimumGainSessions-1)).gte(this.minimumGainPerSession));

        Criteria criteria = new Criteria().andOperator(criteriaList.toArray( new Criteria[criteriaList.size()]));
        return criteria;


    }

    @Override
    public boolean apply(Equity equity) {
        boolean isValid = false;

        try {
            EquityPerformances performances = equity.getPerformances();
            if (performances!=null) {
                List<EquityPerformance> equityPerformanceList = performances.getPerformances();
                if (equityPerformanceList!=null) {
                    isValid = equityPerformanceList.stream()
                            .filter(performance -> performance.getChangePercent().compareTo( getMinimumGainPerSession()) >= 0)
                            .collect(Collectors.toList()).size() >=  getMinimumGainSessions();
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return isValid;
    }
}
