package com.pd.finance.htmlscrapper.marketgainer;

import com.pd.finance.model.EquityPerformances;
import org.jsoup.nodes.Node;

public interface IMarketGainerEquityPerformancesFactory {
    EquityPerformances createMarketGainerEquityPerformances(Node rowNode);
}
