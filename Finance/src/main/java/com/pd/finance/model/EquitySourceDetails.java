package com.pd.finance.model;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class EquitySourceDetails {

    private Set<SourceDetails> sourceDetails = new HashSet<>();

    public Set<SourceDetails> getSourceDetails() {
        return sourceDetails;
    }

    public void setSourceDetails(Set<SourceDetails> sourceDetails) {
        this.sourceDetails = sourceDetails;
    }

    public void addSourceDetails(SourceDetails sourceDetails){
        this.sourceDetails.add(sourceDetails);
    }

    @NotNull
    public SourceDetails getSourceDetails(String sourceName) {
        SourceDetails sourceDetails = null;
        Optional<SourceDetails> first = this.sourceDetails.stream().filter(e -> e.getSourceName().equalsIgnoreCase(sourceName)).findFirst();
        if(first.isPresent()){
            sourceDetails = first.get();
        }
        return sourceDetails;
    }
}
