package com.pd.finance.model;

public class CrawlerResponse {

    private boolean completed;
    private int numObjectsCreated;
    private int numObjectsPersisted;

    public int getNumObjectsCreated() {
        return numObjectsCreated;
    }

    public void setNumObjectsCreated(int numObjectsCreated) {
        this.numObjectsCreated = numObjectsCreated;
    }

    public int getNumObjectsPersisted() {
        return numObjectsPersisted;
    }

    public void setNumObjectsPersisted(int numObjectsPersisted) {
        this.numObjectsPersisted = numObjectsPersisted;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
