package com.pd.finance.filter;

import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityPerformance;
import com.pd.finance.model.EquityPerformances;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class PerformanceFilter  implements  EquityFilter{
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
    public FilterType getFilterType() {
        return FilterType.InCode;
    }

    @Override
    public Criteria getCriteria(String parentObject) {
        return null;
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
