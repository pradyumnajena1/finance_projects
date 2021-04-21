package com.pd.finance.filter.code;

class RecentPeakUpData {
    private boolean allBoundaryIndicesAvailable;
    private boolean strictlyIncreasingBetweenInterval;
    private int numViolations;

    public boolean isAllBoundaryIndicesAvailable() {
        return allBoundaryIndicesAvailable;
    }

    public void setAllBoundaryIndicesAvailable(boolean allBoundaryIndicesAvailable) {
        this.allBoundaryIndicesAvailable = allBoundaryIndicesAvailable;
    }

    public boolean isStrictlyIncreasingBetweenInterval() {
        return strictlyIncreasingBetweenInterval;
    }

    public void setStrictlyIncreasingBetweenInterval(boolean strictlyIncreasingBetweenInterval) {
        this.strictlyIncreasingBetweenInterval = strictlyIncreasingBetweenInterval;
    }

    public int getNumViolations() {
        return numViolations;
    }

    public void setNumViolations(int numViolations) {
        this.numViolations = numViolations;
    }

    public boolean isRecentPeakUpEquity(Integer allowedNumMissesInFlatPeriod) {
        if(!isAllBoundaryIndicesAvailable()){
            return false;
        }
        if(!isStrictlyIncreasingBetweenInterval()){
            return false;
        }
        if(getNumViolations()> allowedNumMissesInFlatPeriod){
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PeakUpData{");
        sb.append("IndAvailable=").append(allBoundaryIndicesAvailable);
        sb.append(", Inc=").append(strictlyIncreasingBetweenInterval);
        sb.append(", miss#=").append(numViolations);
        sb.append('}');
        return sb.toString();
    }
}
