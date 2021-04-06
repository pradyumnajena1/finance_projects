package com.pd.finance.htmlscrapper.marketloser;

import com.pd.finance.model.Equity;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

public interface IMarketLoserEquityFactory {
    List<Equity> fetchMarketLoserEquities(Document document, int maxEquitiesToFetch, String exchange) throws IOException;
}
