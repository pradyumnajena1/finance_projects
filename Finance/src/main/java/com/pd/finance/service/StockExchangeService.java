package com.pd.finance.service;

import com.pd.finance.config.ApplicationConfig;
import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.infra.IObjectConverter;
import com.pd.finance.model.EquityHistoricalData;
import com.pd.finance.model.EquitySearchResponse;
import com.pd.finance.model.EquityStockExchangeDetails;
import com.pd.finance.persistence.StockExchangeDetailsRepository;
import com.pd.finance.request.CreateStockExchangeDetailsRequest;
import com.pd.finance.response.EquityStockExchangeDetailsResponse;
import com.pd.finance.model.EquityIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class StockExchangeService extends AbstractHttpService implements IStockExchangeService {
    private static final Logger logger = LoggerFactory.getLogger(StockExchangeService.class);

    @Autowired
    private ApplicationConfig config;
    @Autowired
    private StockExchangeDetailsRepository stockExchangeDetailsRepository;
    @Autowired
    private IObjectConverter objectConverter;

    public StockExchangeService() {

    }

    @Override
    public List<EquityStockExchangeDetailsResponse> getStockExchangeDetails(EquityIdentifier equityIdentifier) throws ServiceException {

            String searchString  = equityIdentifier.getSearchString();
            String url =  MessageFormat.format(config.getEnvProperty("StockExchangeDetailsUrl"),searchString);
            EquitySearchResponse searchResponse=  get(url, EquitySearchResponse.class);
            return  searchResponse.getStockExchangeDetails();
    }

    @Override
    public EquityStockExchangeDetails create(CreateStockExchangeDetailsRequest createStockExchangeDetailsRequest){
        EquityStockExchangeDetails equityStockExchangeDetails = objectConverter.convert(createStockExchangeDetailsRequest);
        return create(equityStockExchangeDetails);
    }


        @Override
    public EquityStockExchangeDetails  create(EquityStockExchangeDetails equityStockExchangeDetails){
        EquityStockExchangeDetails byExchangeAndStockSymbol = stockExchangeDetailsRepository.findByExchangeAndStockSymbol(equityStockExchangeDetails.getExchange(), equityStockExchangeDetails.getSymbol());
        if(byExchangeAndStockSymbol!=null){
            equityStockExchangeDetails.setId(byExchangeAndStockSymbol.getId());

        }
        EquityStockExchangeDetails save = stockExchangeDetailsRepository.save(equityStockExchangeDetails);
        return save;
    }

    @Override
    public  EquityStockExchangeDetails deleteById(String id) throws ServiceException {
        AtomicReference<EquityStockExchangeDetails> reference = new AtomicReference<>();
        Optional<EquityStockExchangeDetails> optional = stockExchangeDetailsRepository.findById(id);
        optional.ifPresent(ex-> {
            reference.set(ex);
            stockExchangeDetailsRepository.delete(ex);
        });
        optional.orElseThrow(()->new ServiceException(MessageFormat.format( "EquityStockExchangeDetails not found with id:{}",id)));
        return reference.get();
    }
    @Override
    public  EquityStockExchangeDetails deleteByExchangeAndStockSymbol(String exchange, String symbol) throws ServiceException {

         EquityStockExchangeDetails  stockExchangeDetails = stockExchangeDetailsRepository.findByExchangeAndStockSymbol(exchange,symbol);
         if(stockExchangeDetails!=null){
             stockExchangeDetailsRepository.delete(stockExchangeDetails);
         }else{
             throw new ServiceException(MessageFormat.format( "EquityStockExchangeDetails not found with exchange:{} symbol:{}",exchange,symbol));
         }

        return stockExchangeDetails;
    }

    @Override
    public  EquityStockExchangeDetails findById(String id){
        AtomicReference<EquityStockExchangeDetails> reference = new AtomicReference<>();
         Optional<EquityStockExchangeDetails> optional = stockExchangeDetailsRepository.findById(id);
          optional.ifPresent(ex-> reference.set(ex));
          return reference.get();
    }

    @Override
    public Page<EquityStockExchangeDetails> findByExchange(String exchange, Pageable pageable){
        Page<EquityStockExchangeDetails> page = stockExchangeDetailsRepository.findByExchange(exchange, pageable);
        return page;
    }

    @Override
    public Page<EquityStockExchangeDetails> findAll(Pageable pageable){
        Page<EquityStockExchangeDetails> page = stockExchangeDetailsRepository.findAll(pageable);
        return page;
    }



}
