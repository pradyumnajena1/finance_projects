package com.pd.finance.htmlscrapper.equity;

import com.pd.finance.model.ShareholdingDetails;
import com.pd.finance.model.TechnicalDetails;
import org.jsoup.nodes.Document;

public interface IShareholdingPatternFactory {
    ShareholdingDetails create(Document document);
}
