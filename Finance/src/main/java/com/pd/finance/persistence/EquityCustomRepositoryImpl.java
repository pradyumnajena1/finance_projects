package com.pd.finance.persistence;

import com.pd.finance.model.Equity;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.function.LongSupplier;


public class EquityCustomRepositoryImpl implements EquityCustomRepository{

    private final MongoTemplate mongoTemplate;

    @Autowired
    public EquityCustomRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Page<Equity> searchEquity(Criteria criteria , Pageable pageable,Sort sort) {

        final Query query = new Query(criteria).with(sort).with(pageable);

        return searchEquities(query, pageable);
    }

    @Override
    public Page<Equity> searchEquity(Criteria criteria , Pageable pageable ) {

        final Query query = new Query(criteria).with(pageable);

        return searchEquities(query, pageable);
    }

    @Override
    public Page<Equity> searchEquity(Query query, Pageable pageable) {
        return searchEquities(query, pageable);
    }

    @NotNull
    protected Page<Equity> searchEquities(Query query, Pageable pageable) {
        List<Equity> list = mongoTemplate.find(query, Equity.class);
        LongSupplier totalSupplier = () -> mongoTemplate.count((Query.of(query).limit(-1).skip(-1)), Equity.class);
        Page<Equity> page = PageableExecutionUtils.getPage(list, pageable, totalSupplier);
        return page;
    }


}
