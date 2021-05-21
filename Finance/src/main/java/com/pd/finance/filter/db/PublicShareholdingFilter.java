package com.pd.finance.filter.db;

import org.springframework.data.mongodb.core.query.Criteria;

public class PublicShareholdingFilter extends BaseShareholdingFilter{
    @Override
    public Criteria getCriteria(String parentObject) {
        return super.getCriteria(parentObject+".publicShareholding");
    }
}
