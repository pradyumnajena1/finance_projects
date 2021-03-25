package com.pd.finance.model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EquitySourceDetails {

    private List<SourceDetails> sourceDetails = new ArrayList<>();

    public List<SourceDetails> getSourceDetails() {
        return sourceDetails;
    }

    public void setSourceDetails(List<SourceDetails> sourceDetails) {
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
