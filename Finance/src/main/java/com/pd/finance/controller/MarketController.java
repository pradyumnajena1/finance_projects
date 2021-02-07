package com.pd.finance.controller;

import com.pd.finance.model.Equity;
import com.pd.finance.request.MarketGainersRequest;
import com.pd.finance.response.BaseResponse;
import com.pd.finance.service.IMarket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



@RestController
public class MarketController {
    Logger logger = LoggerFactory.getLogger(MarketController.class);
    @Autowired
    private IMarket market;


    @RequestMapping(value = "/market/nse/gainers",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse index(@RequestBody MarketGainersRequest request) {

        try {
          List<Equity> equities =  market.GetGainers(request);
           return new BaseResponse(equities);
        } catch (Exception e) {
            logger.error("Failed to process Gainers",e);
            return  new BaseResponse(e);
        }

    }
}
