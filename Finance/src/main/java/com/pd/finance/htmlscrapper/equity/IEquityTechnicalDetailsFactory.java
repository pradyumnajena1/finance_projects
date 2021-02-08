package com.pd.finance.htmlscrapper.equity;

import com.pd.finance.model.TechnicalDetails;
import org.jsoup.nodes.Document;

public interface IEquityTechnicalDetailsFactory {
    TechnicalDetails create(Document document);
}
