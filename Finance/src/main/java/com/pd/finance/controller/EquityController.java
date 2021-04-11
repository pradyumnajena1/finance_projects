package com.pd.finance.controller;


import com.pd.finance.exceptions.EquityNotFoundException;
import com.pd.finance.exceptions.PersistenceException;
import com.pd.finance.model.Equity;
import com.pd.finance.request.BulkCreateEquityRequest;
import com.pd.finance.request.CreateEquityRequest;
import com.pd.finance.request.EquityBulkUpdateRequest;
import com.pd.finance.response.BaseResponse;
import com.pd.finance.response.chart.EquityBulkUpdateResponse;
import com.pd.finance.service.EquityService;
import com.pd.finance.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EquityController {
    Logger logger = LoggerFactory.getLogger(EquityController.class);

    @Autowired
    private EquityService equityService;


    @RequestMapping(value = "/equities/{id}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse getEquityById(@PathVariable("id") String id){

        try {
            Equity equity = equityService.getEquity(id);
            return new BaseResponse(equity);
        } catch (EquityNotFoundException e) {
            logger.error(e.getMessage(),e);
            return new BaseResponse(e);
        }
    }

    @RequestMapping(value = "/equities",method = RequestMethod.POST,consumes =MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse createEquity(@RequestBody CreateEquityRequest request ){

        try {
            logger.info("createEquity exec started for equity request {}",request);
            Equity equity = equityService.createEquity(request);
            logger.info("createEquity exec completed for equity request {}",request);
            return new BaseResponse(equity);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new BaseResponse(e);
        }
    }
    @RequestMapping(value = "/equities/bulkCreate",method = RequestMethod.POST,consumes =MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse bulkCreateEquity(@RequestBody BulkCreateEquityRequest request ){

        try {
            logger.info("createEquity exec started for equity request {}",request);
            List<Equity> equities = equityService.createEquity(request);
            logger.info("createEquity exec completed for equity request {}",request);
            return new BaseResponse(equities);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new BaseResponse(e);
        }
    }



    @RequestMapping(value = "/equities/bulkUpdate",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse updateEquities(@RequestBody EquityBulkUpdateRequest request ){

        try {
            EquityBulkUpdateResponse bulkUpdateResponse = equityService.updateEquities(request);
            return new BaseResponse(bulkUpdateResponse);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new BaseResponse(e);
        }
    }



}
