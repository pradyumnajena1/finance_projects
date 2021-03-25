package com.pd.finance.model;

import java.util.HashMap;
import java.util.Map;

public class SourceDetails {


    private String sourceName;
    private String EquityName;
    private String sourceUrl;

    private Map<String,String> additionalAttributes = new HashMap<>();

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getEquityName() {
        return EquityName;
    }

    public void setEquityName(String equityName) {
        EquityName = equityName;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public Map<String, String> getAdditionalAttributes() {
        return additionalAttributes;
    }
}
