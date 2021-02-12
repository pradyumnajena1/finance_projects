package com.pd.finance.controller;


import com.pd.finance.exceptions.EquityNotFoundException;
import com.pd.finance.exceptions.PersistenceException;
import com.pd.finance.model.Equity;
import com.pd.finance.response.BaseResponse;
import com.pd.finance.service.EquityService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

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

    @RequestMapping(value = "/equities",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse getEquityByBseId(@RequestParam(value = "bseId",required = false) final String bseId,
                                         @RequestParam(value = "nseId",required = false) final String nseId,
                                         @RequestParam(value = "name",required = false) final String name){

        try {
            Equity equity = null;
            if (StringUtils.isNotBlank( bseId )){
                equity = equityService.getEquityByBseId(bseId);
            }else if(StringUtils.isNotBlank(nseId)){
                equity = equityService.getEquityByNseId(nseId);
            }else if(StringUtils.isNotBlank(name )){
                equity = equityService.getEquityByName(name);
            }

            return new BaseResponse(equity);
        } catch (EquityNotFoundException e) {
            logger.error(e.getMessage(),e);
            return new BaseResponse(e);
        } catch (PersistenceException e) {
            logger.error(e.getMessage(),e);
            return new BaseResponse(e);
        }
    }


}
