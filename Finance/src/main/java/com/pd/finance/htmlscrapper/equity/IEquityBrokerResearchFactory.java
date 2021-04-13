package com.pd.finance.htmlscrapper.equity;

import com.pd.finance.model.BrokerResearchDetails;
import com.pd.finance.model.EquityEssentials;
import org.jsoup.nodes.Document;

public interface IEquityBrokerResearchFactory {
    BrokerResearchDetails create(Document document);
}
