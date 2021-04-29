package com.pd.finance.htmlscrapper.equity;

import com.pd.finance.model.EquityProsAndConsDetails;
import org.jsoup.nodes.Document;

public interface IEquityProsAndConsDetailsFactory {
    EquityProsAndConsDetails create(Document document);
}
