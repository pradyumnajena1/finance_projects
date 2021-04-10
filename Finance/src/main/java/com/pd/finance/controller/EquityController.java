package com.pd.finance.controller;


import com.pd.finance.exceptions.EquityNotFoundException;
import com.pd.finance.model.Equity;
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
            Equity equity = equityService.createEquityWithMandatoryAttributes(request.getName(), request.getSrcUrl(), request.getExchange(), Constants.SOURCE_MONEY_CONTROL);
            equity = equityService.upsertEquity(equity);
            boolean success   = equityService.fetchAndPersistEquityAttributes(equity);
            if(success){
                equity = equityService.getEquity(equity.getDefaultEquityIdentifier());
            }
            return new BaseResponse(equity);
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
