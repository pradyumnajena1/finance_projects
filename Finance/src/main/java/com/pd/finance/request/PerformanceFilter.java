package com.pd.finance.request;

import java.math.BigDecimal;

public class PerformanceFilter {

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
}
