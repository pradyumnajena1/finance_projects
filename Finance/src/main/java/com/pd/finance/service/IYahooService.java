package com.pd.finance.service;

import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.model.EquityHistoricalIntervalData;
import com.pd.finance.model.EquityIdentifier;
import com.pd.finance.model.EquityStockExchangeDetails;
import com.pd.finance.request.CreateStockExchangeDetailsRequest;
import com.pd.finance.response.EquityStockExchangeDetailsResponse;
import com.pd.finance.response.chart.ChartResponse;
import com.pd.finance.response.summary.EquitySummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.temporal.TemporalAmount;
import java.util.List;

public interface IYahooService {
    EquitySummaryResponse getEquitySummary(EquityIdentifier identifier)throws ServiceException;
    ChartResponse getEquityChart(EquityIdentifier equityIdentifier) throws ServiceException;

    EquityHistoricalIntervalData getHistoricalStockPrice(EquityIdentifier identifier, TemporalAmount amountToSubtract, HistoricalDataInterval interval) throws ServiceException;

    EquityStockExchangeDetailsResponse getStockExchangeDetails(EquityIdentifier equityIdentifier) throws ServiceException;


    EquityStockExchangeDetails create(CreateStockExchangeDetailsRequest createStockExchangeDetailsRequest);

    EquityStockExchangeDetails  create(EquityStockExchangeDetails equityStockExchangeDetails);

    EquityStockExchangeDetails deleteById(String id) throws ServiceException;

    EquityStockExchangeDetails deleteByExchangeAndStockSymbol(String exchange, String symbol) throws ServiceException;

    EquityStockExchangeDetails findById(String id);

    Page<EquityStockExchangeDetails> findByExchange(String exchange, Pageable pageable);

    Page<EquityStockExchangeDetails> findAll(Pageable pageable);

    List<EquityStockExchangeDetails> findByLongName(String longName);

    List<EquityStockExchangeDetails> findByShortName(String shortName);

    List<EquityStockExchangeDetails> findByName(String name);
}
