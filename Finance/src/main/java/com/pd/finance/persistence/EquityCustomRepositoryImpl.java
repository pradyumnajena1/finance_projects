package com.pd.finance.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class EquityCustomRepositoryImpl implements EquityCustomRepository{

    private final MongoTemplate mongoTemplate;

    @Autowired
    public EquityCustomRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
}
