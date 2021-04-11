package com.pd.finance.persistence;

import com.pd.finance.model.Equity;
import com.pd.finance.model.portfolio.Portfolio;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends MongoRepository<Portfolio, String> {
}
