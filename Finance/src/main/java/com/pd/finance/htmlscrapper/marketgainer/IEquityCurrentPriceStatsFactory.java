package com.pd.finance.htmlscrapper.marketgainer;

import com.pd.finance.model.EquityCurrentPriceStats;
import org.jsoup.nodes.Node;

public interface IEquityCurrentPriceStatsFactory {
    EquityCurrentPriceStats createEquityCurrentPriceStats(Node rowNode);
}
