package com.pd.finance.htmlscrapper.equity;

import com.pd.finance.model.EquityProfitLossDetails;
import com.pd.finance.model.EquitySwotDetails;
import org.jsoup.nodes.Document;

public interface IEquityProfitLossDetailsFactory {
    EquityProfitLossDetails create(Document document);
}
