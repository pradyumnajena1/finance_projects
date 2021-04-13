package com.pd.finance.model;

import java.util.Set;
import java.util.TreeSet;

public class BrokerResearchDetails extends EquityAttribute {
    private Set<BrokerResearchLineItem> brokerResearchLineItems = new TreeSet<>();

    public Set<BrokerResearchLineItem> getBrokerResearchLineItems() {
        return brokerResearchLineItems;
    }

    public void setBrokerResearchLineItems(Set<BrokerResearchLineItem> brokerResearchLineItems) {
        this.brokerResearchLineItems = new TreeSet<>( brokerResearchLineItems);
    }
}
