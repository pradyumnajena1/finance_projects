package com.pd.finance.htmlscrapper.marketgainer;

import com.pd.finance.model.Equity;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

public interface IMarketGainerEquityFactory {
    List<Equity> fetchMarketGainerEquities(Document document) throws IOException;
}
