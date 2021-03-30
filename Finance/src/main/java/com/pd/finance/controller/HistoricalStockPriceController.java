package com.pd.finance.controller;

import com.pd.finance.exceptions.EquityNotFoundException;
import com.pd.finance.exceptions.PersistenceException;
import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityIdentifier;
import com.pd.finance.response.BaseResponse;
import com.pd.finance.service.EquityHistoricalStockPriceAttributeService;
import com.pd.finance.service.IEquityService;
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
public class HistoricalStockPriceController {
    Logger logger = LoggerFactory.getLogger(HistoricalStockPriceController.class);

    @Autowired
    private IEquityService equityService;

    @Autowired
    private IHistoricalStockPriceService historicalStockPriceService;


    @RequestMapping(value = "/equities/stock_price_history/{id}",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse updateEquityHistoricalStockPrices(@PathVariable("id") String id){

        try {
            Equity equity = equityService.getEquity(id);

            equity= historicalStockPriceService.updateEquityHistoricalStockPrice(equity);

            return new BaseResponse(equity);

        } catch (EquityNotFoundException | ServiceException  e) {
            logger.error(e.getMessage(),e);
            return new BaseResponse(e);
        }
    }

    @RequestMapping(value = "/equities/stock_price_history",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse updateAllEquityHistoricalStockPrices(){

        try {
            historicalStockPriceService.updateAllEquityHistoricalStockPrice();
            return new BaseResponse("Successfully updated all equities historical stock price");

        } catch ( ServiceException  e) {
            logger.error(e.getMessage(),e);
            return new BaseResponse(e);
        }
    }
}
