package com.pd.finance.model;

import java.util.HashMap;
import java.util.Map;

public class CacheStatistics {

    private Map<String,Integer> stat;

    public CacheStatistics() {
        stat= new HashMap<>();
    }

    public Map<String, Integer> getStat() {
        return stat;
    }
}
