package com.pd.finance.htmlscrapper.equity;

import com.pd.finance.model.EquitySwotDetails;
import org.jsoup.nodes.Document;

public interface IEquitySwotFactory {
    EquitySwotDetails create(Document document);
}
