package com.pd.finance.htmlscrapper.equity;

import com.pd.finance.model.EquityEssentials;
import com.pd.finance.model.FundamentalRatios;
import org.jsoup.nodes.Document;

public interface IEquityFundamentalRatioFactory {
    FundamentalRatios create(Document document);
}
