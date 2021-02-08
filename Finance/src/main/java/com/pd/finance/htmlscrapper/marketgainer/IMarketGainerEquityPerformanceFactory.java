package com.pd.finance.htmlscrapper.marketgainer;

import com.pd.finance.model.EquityPerformance;
import org.jsoup.nodes.Node;

public interface IMarketGainerEquityPerformanceFactory {
    EquityPerformance create(Node performanceNode);
}
