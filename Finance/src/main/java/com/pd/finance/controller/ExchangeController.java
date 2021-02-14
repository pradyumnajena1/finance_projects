package com.pd.finance.controller;

import com.pd.finance.infra.IObjectConverter;
import com.pd.finance.model.EquityStockExchangeDetails;
import com.pd.finance.request.CreateStockExchangeDetailsRequest;
import com.pd.finance.response.BaseResponse;
import com.pd.finance.service.IStockExchangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ExchangeController {
    Logger logger = LoggerFactory.getLogger(ExchangeController.class);

    @Autowired
    private IStockExchangeService stockExchangeService;

    @Autowired
    private IObjectConverter objectConverter;


    @RequestMapping(value = "/stockexchange/{id}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE )
    public BaseResponse getStockExchangeDetails(@PathVariable String id){
        Page<EquityStockExchangeDetails> page = null;
        try {
            EquityStockExchangeDetails byId = stockExchangeService.findById(id);
            return new BaseResponse(byId);

        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new BaseResponse(e);
        }
    }
    @RequestMapping(value = "/stockexchange/{id}",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_VALUE )
    public BaseResponse deleteStockExchangeDetails(@PathVariable String id){
        Page<EquityStockExchangeDetails> page = null;
        try {
            EquityStockExchangeDetails byId = stockExchangeService.deleteById(id);
            return new BaseResponse(byId);

        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new BaseResponse(e);
        }
    }

    @RequestMapping(value = "/stockexchange",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_VALUE )
    public BaseResponse deleteStockExchangeDetailsByExchangeAndSymbol(@RequestParam String exchange,@RequestParam String symbol){
        Page<EquityStockExchangeDetails> page = null;
        try {
            EquityStockExchangeDetails byId = stockExchangeService.deleteByExchangeAndStockSymbol(exchange,symbol);
            return new BaseResponse(byId);

        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new BaseResponse(e);
        }
    }

    @RequestMapping(value = "/stockexchange",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE )
    public BaseResponse getStockExchangeDetails(@RequestParam(value = "exchange",required = false) Optional< String> exchange, Pageable pageable){
        Page<EquityStockExchangeDetails> page = null;
        try {
            if(exchange.isPresent()){
                page =  stockExchangeService.findByExchange(exchange.get(),pageable);
            }else{
                page =  stockExchangeService.findAll(pageable);
            }
            return new BaseResponse(page);

        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new BaseResponse(e);
        }
    }

    @RequestMapping(value = "/stockexchange",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE ,consumes =  MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse createStockExchangeDetails(@RequestBody CreateStockExchangeDetailsRequest exchangeDetailsRequest){

        try {

            EquityStockExchangeDetails equityStockExchangeDetails = stockExchangeService.create(exchangeDetailsRequest);

            return new BaseResponse(equityStockExchangeDetails);

        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new BaseResponse(e);
        }
    }


}
