package com.pd.finance.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SourceDetails that = (SourceDetails) o;
        return sourceName.equals(that.sourceName) &&
                EquityName.equals(that.EquityName) &&
                sourceUrl.equals(that.sourceUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceName, EquityName, sourceUrl);
    }
}
