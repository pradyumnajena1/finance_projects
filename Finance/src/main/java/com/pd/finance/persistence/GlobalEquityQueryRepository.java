package com.pd.finance.persistence;

import com.pd.finance.model.GlobalEquityQuery;
import com.pd.finance.model.UserEquityQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalEquityQueryRepository extends MongoRepository<GlobalEquityQuery, Long>, QueryByExampleExecutor<GlobalEquityQuery> {
}
