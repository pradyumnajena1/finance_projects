package com.pd.finance.response.chart;

public class EquityBulkUpdateResponse {
    private boolean completed;
    private int numObjectsToUpdate;
    private int numObjectsUpdated;

    public int getNumObjectsToUpdate() {
        return numObjectsToUpdate;
    }

    public void setNumObjectsToUpdate(int numObjectsToUpdate) {
        this.numObjectsToUpdate = numObjectsToUpdate;
    }

    public int getNumObjectsUpdated() {
        return numObjectsUpdated;
    }

    public void setNumObjectsUpdated(int numObjectsUpdated) {
        this.numObjectsUpdated = numObjectsUpdated;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
