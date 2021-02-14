package com.pd.finance.persistence;

import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityStockExchangeDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface StockExchangeDetailsRepository extends MongoRepository<EquityStockExchangeDetails, String> {

    @Query(value="{ 'exchange' : ?0 }")
    Page<EquityStockExchangeDetails> findByExchange(String exchange, Pageable page);

    @Query(value="{ 'exchange' : ?0 , 'symbol' : ?1}")
    EquityStockExchangeDetails findByExchangeAndStockSymbol(String exchange,String stockSymbol);

}
