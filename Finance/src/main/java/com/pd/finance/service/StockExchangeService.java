package com.pd.finance.service;

import com.pd.finance.config.ApplicationConfig;
import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.infra.IObjectConverter;
import com.pd.finance.model.EquitySearchResponse;
import com.pd.finance.model.EquityStockExchangeDetails;
import com.pd.finance.persistence.StockExchangeDetailsRepository;
import com.pd.finance.request.CreateStockExchangeDetailsRequest;
import com.pd.finance.response.EquityStockExchangeDetailsResponse;
import com.pd.finance.model.EquityIdentifier;
import okhttp3.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Resource(name="rateLimitInterceptor")
    private Interceptor rateLimitInterceptor;

    public StockExchangeService() {

    }




    @Override
    public EquityStockExchangeDetailsResponse getStockExchangeDetails(EquityIdentifier equityIdentifier) throws ServiceException {

         EquityStockExchangeDetailsResponse result = null;
            String searchString  = equityIdentifier.getSearchString();
            String url =  MessageFormat.format(config.getEnvProperty("StockExchangeDetailsUrl"),searchString);
            EquitySearchResponse searchResponse=  get(url, EquitySearchResponse.class);
            if(searchResponse!=null){

                List<EquityStockExchangeDetailsResponse> stockExchangeDetails1 = searchResponse.getStockExchangeDetails();
                Optional<EquityStockExchangeDetailsResponse> first = stockExchangeDetails1.stream().filter(detailsResponse -> detailsResponse.getExchange().equals(equityIdentifier.getExchange())).findFirst();
                if(first.isPresent()){
                    result = first.get();
                }

            }

        return result;
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
    @Override
    public List<EquityStockExchangeDetails> findByLongName(String longName){
        return stockExchangeDetailsRepository.findByLongName(longName);
    }
    @Override
    public List<EquityStockExchangeDetails> findByShortName(String shortName){
        return stockExchangeDetailsRepository.findByLongName(shortName);
    }
    @Override
    public List<EquityStockExchangeDetails> findByName(String name){
        List<EquityStockExchangeDetails> existingDetails = null;
        existingDetails = stockExchangeDetailsRepository.findByLongName(name);

        if(existingDetails == null || existingDetails.isEmpty()){
            existingDetails = stockExchangeDetailsRepository.findByShortName(name);

        }

        return existingDetails ;
    }

    @Override
    protected List<Interceptor> getInterceptors() {
        return new ArrayList<>(Arrays.asList(rateLimitInterceptor));
    }
}
