package com.pd.finance.htmlscrapper.equity;

import com.pd.finance.model.EquityOverview;
import org.jsoup.nodes.Document;

public interface IEquityOverviewFactory {
    EquityOverview create(Document document);
}
