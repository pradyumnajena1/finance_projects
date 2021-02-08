package com.pd.finance.htmlscrapper.equity;

import com.pd.finance.model.EquityEssentials;
import org.jsoup.nodes.Document;

public interface IEquityEssentialsFactory {
    EquityEssentials create(Document document);
}
