package com.pd.finance.htmlscrapper.equity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pd.finance.model.EquitySwotDetails;
import com.pd.finance.model.TechnicalDetails;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EquityTechnicalDetailsFactory implements IEquityTechnicalDetailsFactory {

    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(EquitySwotFactory.class);

    @Override
    public TechnicalDetails create(Document document){
        return null;
    }
}
