package com.pd.finance.persistence;

import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityStockExchangeDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface StockExchangeDetailsRepository extends MongoRepository<EquityStockExchangeDetails, String> {

    @Query(value = "{'exchange': {$regex : ?0, $options: 'i'}}")
    Page<EquityStockExchangeDetails> findByExchange(String exchange, Pageable page);

    @Query(value = "{'exchange': {$regex : ?0, $options: 'i'},'symbol': {$regex : ?1, $options: 'i'}}")
    EquityStockExchangeDetails findByExchangeAndStockSymbol(String exchange,String stockSymbol);

    @Query(value = "{'longName': {$regex : ?0, $options: 'i'}}")
    List<EquityStockExchangeDetails> findByLongName(String longName);

    @Query(value = "{'shortName': {$regex : ?0, $options: 'i'}}")
    List<EquityStockExchangeDetails> findByShortName(String shortName);



}
