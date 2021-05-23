package com.pd.finance.filter.db;

import org.springframework.data.mongodb.core.query.Criteria;

public class RoceFilter extends BaseFundamentalRatioFilter{

    @Override
    public Criteria getCriteria(String parentObject) {
        return super.getCriteria(parentObject+".roce");
    }
}
