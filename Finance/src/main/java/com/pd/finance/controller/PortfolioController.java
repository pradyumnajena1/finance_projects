package com.pd.finance.controller;

import com.pd.finance.exceptions.ValidationException;
import com.pd.finance.model.Equity;
import com.pd.finance.model.portfolio.Portfolio;
import com.pd.finance.request.CreatePortfolioRequest;
import com.pd.finance.request.UpdatePortfolioRequest;
import com.pd.finance.response.BaseResponse;
import com.pd.finance.service.portfolio.IPortfolioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class PortfolioController {
    Logger logger = LoggerFactory.getLogger(EquityController.class);

    @Autowired
    private IPortfolioService portfolioService;


    @RequestMapping(value = "/portfolios",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse createPortfolio(@RequestBody CreatePortfolioRequest request){
        try {
            logger.info("createPortfolio exec started for CreatePortfolioRequest {}",request);

            if(!request.isValid()){
                throw new ValidationException(request.getValidationErrors());
            }
            Portfolio portfolio = portfolioService.createPortfolio(request);
            logger.info("createPortfolio exec completed for CreatePortfolioRequest {}",request);
            return new BaseResponse(portfolio);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new BaseResponse(e);
        }
    }

    @RequestMapping(value = "/portfolios/{id}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse getPortfolio(@PathVariable("id") String id){
        try {
            logger.info("getPortfolio exec started for portfolioId   {}",id);


            Portfolio portfolio = portfolioService.getPortfolio(id);
            logger.info("getPortfolio exec completed for portfolioId {}",id);
            return new BaseResponse(portfolio);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new BaseResponse(e);
        }
    }

    @RequestMapping(value = "/portfolios/{id}",method = RequestMethod.PATCH,consumes = MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse updatePortfolio(@PathVariable("id") String id,@RequestBody UpdatePortfolioRequest request){
        try {
            logger.info("updatePortfolio exec started for portfolioId   {}",id);


            Portfolio portfolio = portfolioService.updatePortfolio(request);
            logger.info("updatePortfolio exec completed for portfolioId {}",id);
            return new BaseResponse(portfolio);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new BaseResponse(e);
        }
    }
    @RequestMapping(value = "/portfolios/{id}",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse deletePortfolio(@PathVariable("id") String id){
        try {
            logger.info("deletePortfolio exec started for portfolioId   {}",id);


            Portfolio portfolio = portfolioService.deletePortfolio(id);
            logger.info("deletePortfolio exec completed for portfolioId {}",id);
            return new BaseResponse(portfolio);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new BaseResponse(e);
        }
    }
}
