package com.pd.finance.persistence;

import com.pd.finance.model.Equity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.support.QuerydslMongoPredicateExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquityRepository extends MongoRepository<Equity, String> ,EquityCustomRepository  {

    @Query(value="{ 'name' : ?0 }")
    public Equity findByName(String name);

    @Query(value="{ 'nseId' : ?0 }")
    public Equity findByNseId(String nseId);

    @Query(value="{ 'bseId' : ?0 }")
    public Equity findByBseId(String bseId);

    @Query(value="{'name' : {'$in' : [names…​]}}")
    public List<Equity> findByNameIn(List<String> names);

    @Query(value="{'$and':[{ 'exchange' : ?0 },{ 'symbol' : ?1 }]}")
    public Equity findByExchangeAndSymbol(String exchange,String symbol);


}
