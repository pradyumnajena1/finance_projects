package com.pd.finance.htmlscrapper.equity;

import com.pd.finance.model.EquityEssentials;
import com.pd.finance.model.EquityInsights;
import org.jsoup.nodes.Document;

public interface IEquityInsightsFactory {
    EquityInsights create(Document document);
}
