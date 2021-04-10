package com.pd.finance.model.equity.summary;


import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.ArrayList;
import java.util.List;

public class RecommendationTrend {

    private List<Trend> trend = new ArrayList<Trend>();

    @Field(targetType = FieldType.INT64)
    private Long maxAge;



    public List<Trend> getTrend() {
        return trend;
    }


    public void setTrend(List<Trend> trend) {
        this.trend = trend;
    }


    public Long getMaxAge() {
        return maxAge;
    }


    public void setMaxAge(Long maxAge) {
        this.maxAge = maxAge;
    }
}
