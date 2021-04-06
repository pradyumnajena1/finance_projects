package com.pd.finance.model;

import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class EquityPerformances  extends EquityAttribute{

    private final String type;
    private final List<EquityPerformance> performances;
    private List<BigDecimal> sortedGainPercentages;

    public EquityPerformances(String type, List<EquityPerformance> performances) {

        this.type = type;
        this.performances = performances;
        this.sortedGainPercentages = getSortedGainPercentages(this.performances);
    }

    public String getType() {
        return type;
    }

    public List<EquityPerformance> getPerformances() {
        return performances;
    }

    public List<BigDecimal> getSortedGainPercentages() {
        return sortedGainPercentages;
    }

    private List<BigDecimal> getSortedGainPercentages(List<EquityPerformance> performances){
        List<BigDecimal> gains = performances.stream().map(performance -> performance.getChangePercent()).collect(Collectors.toList());
        Collections.sort(gains, Comparator.reverseOrder());
        return gains;
    }
}
