package com.pd.finance.htmlscrapper.equity;

import com.pd.finance.model.EquityEssentials;
import com.pd.finance.model.InsiderTransactionDetails;
import org.jsoup.nodes.Document;

public interface IEquityInsiderTransactionDetailsFactory {
    InsiderTransactionDetails create(Document document);
}
