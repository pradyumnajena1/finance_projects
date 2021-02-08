package com.pd.finance.request;

import com.pd.finance.utils.CommonUtils;

public class DebugFilter {

    private int numEquities= CommonUtils.FetchAllEquities;

    public DebugFilter() {
    }

    public int getNumEquities() {
        return numEquities;
    }

    public void setNumEquities(int numEquities) {
        this.numEquities = numEquities;
    }
}
