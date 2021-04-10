package com.pd.finance.controller;

import com.pd.finance.exceptions.EquityNotFoundException;
import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.model.Equity;
import com.pd.finance.response.BaseResponse;
import com.pd.finance.service.IEquityService;
import com.pd.finance.service.IEquitySummaryServiceService;
import com.pd.finance.service.IHistoricalStockPriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EquitySummaryController {
    Logger logger = LoggerFactory.getLogger(EquitySummaryController.class);

    @Autowired
    private IEquityService equityService;

    @Autowired
    private IEquitySummaryServiceService equitySummaryServiceService;

    @RequestMapping(value = "/equities/summary/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse updateEquitySummary(@PathVariable("id") String id) {

        try {
            Equity equity = equityService.getEquity(id);

            equity = equitySummaryServiceService.updateEquitySummary(equity);

            return new BaseResponse(equity);

        } catch (EquityNotFoundException | ServiceException e) {
            logger.error(e.getMessage(), e);
            return new BaseResponse(e);
        }
    }

    @RequestMapping(value = "/equities/summary/exchange/{exchange}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse updateAllEquitySummary(@PathVariable("exchange") String exchange) {

        try {
            equitySummaryServiceService.updateAllEquitySummary(exchange);
            return new BaseResponse("Successfully updated all equities summary");

        } catch (ServiceException e) {
            logger.error(e.getMessage(), e);
            return new BaseResponse(e);
        }
    }
}
