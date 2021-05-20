package com.pd.finance.htmlscrapper.equity;

import com.pd.finance.model.EquityDealsDetails;
import com.pd.finance.model.EquityOverview;
import org.jsoup.nodes.Document;

public interface IEquityDealsDetailsFactory {
    EquityDealsDetails create(Document document);
}
