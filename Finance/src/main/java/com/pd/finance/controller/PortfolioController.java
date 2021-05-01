package com.pd.finance.controller;

import com.pd.finance.exceptions.ValidationException;
import com.pd.finance.model.Equity;
import com.pd.finance.model.portfolio.Portfolio;
import com.pd.finance.request.*;
import com.pd.finance.response.BaseResponse;
import com.pd.finance.service.portfolio.IPortfolioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class PortfolioController {
    private static Logger logger = LoggerFactory.getLogger(EquityController.class);

    @Autowired
    private IPortfolioService portfolioService;


    @RequestMapping(value = "/users/{userId}/portfolios",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse createPortfolio(@PathVariable(value = "userId") Long userId,@RequestBody CreatePortfolioRequest request){
        try {
            logger.info("createPortfolio exec started for CreatePortfolioRequest {}",request);

            if(!request.isValid()){
                throw new ValidationException(request.getValidationErrors());
            }
            Portfolio portfolio = portfolioService.createPortfolio(userId,request);
            logger.info("createPortfolio exec completed for CreatePortfolioRequest {}",request);
            return new BaseResponse(portfolio);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new BaseResponse(e);
        }
    }

    @RequestMapping(value = "/users/{userId}/portfolios/{portfolioId}/equities",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse addPortfolioEquity(@PathVariable(value = "userId") Long userId,@PathVariable(value = "portfolioId") Long portfolioId,@RequestBody AddPortfolioEquityRequest request){
        try {
            logger.info("addPortfolioEquity exec started for AddPortfolioEquityRequest {}",request);

            if(!request.isValid()){
                throw new ValidationException(request.getValidationErrors());
            }
            Portfolio portfolio = portfolioService.addPortfolioEquities(userId,portfolioId,request);
            logger.info("createPortfolio exec completed for CreatePortfolioRequest {}",request);
            return new BaseResponse(portfolio);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new BaseResponse(e);
        }
    }

    @RequestMapping(value = "/users/{userId}/portfolios/{portfolioId}/equities/{portfolioEquityId}/lots",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse addPortfolioEquityLots(@PathVariable(value = "userId") Long userId,@PathVariable(value = "portfolioId") Long portfolioId,@PathVariable(value = "portfolioEquityId") String portfolioEquityId,@RequestBody AddPortfolioEquityLotsRequest request){
        try {
            logger.info("addPortfolioEquity exec started for AddPortfolioEquityRequest {}",request);

            if(!request.isValid()){
                throw new ValidationException(request.getValidationErrors());
            }
            Portfolio portfolio = portfolioService.addPortfolioEquityLots(userId,portfolioId,portfolioEquityId,request);
            logger.info("createPortfolio exec completed for CreatePortfolioRequest {}",request);
            return new BaseResponse(portfolio);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new BaseResponse(e);
        }
    }

    @RequestMapping(value = "/users/{userId}/portfolios/{portfolioId}/equities/{portfolioEquityId}/lots",method = RequestMethod.DELETE,consumes = MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse deletePortfolioEquityLots(@PathVariable(value = "userId") Long userId,@PathVariable(value = "portfolioId") Long portfolioId,@PathVariable(value = "portfolioEquityId") String portfolioEquityId,@RequestBody DeletePortfolioEquityLotsRequest request){
        try {
            logger.info("addPortfolioEquity exec started for AddPortfolioEquityRequest {}",request);

            if(!request.isValid()){
                throw new ValidationException(request.getValidationErrors());
            }
            Portfolio portfolio = portfolioService.deletePortfolioEquityLots(userId,portfolioId,portfolioEquityId,request);
            logger.info("createPortfolio exec completed for CreatePortfolioRequest {}",request);
            return new BaseResponse(portfolio);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new BaseResponse(e);
        }
    }


    @RequestMapping(value = "/users/{userId}/portfolios/{portfolioId}/equities",method = RequestMethod.DELETE,consumes = MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse deletePortfolioEquity(@PathVariable(value = "userId") Long userId,@PathVariable(value = "portfolioId") Long portfolioId,@RequestBody DeletePortfolioEquityRequest request){
        try {
            logger.info("deletePortfolioEquity exec started for AddPortfolioEquityRequest {}",request);

            if(!request.isValid()){
                throw new ValidationException(request.getValidationErrors());
            }
            Portfolio portfolio = portfolioService.deletePortfolioEquities(userId,portfolioId,request);
            logger.info("deletePortfolioEquity exec completed for CreatePortfolioRequest {}",request);
            return new BaseResponse(portfolio);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new BaseResponse(e);
        }
    }

    @RequestMapping(value = "/users/{userId}/portfolios/{id}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse getPortfolio(@PathVariable(value = "userId") Long userId,@PathVariable("id") Long id){
        try {
            logger.info("getPortfolio exec started for portfolioId   {}",id);


            Portfolio portfolio = portfolioService.getPortfolio(userId,id);
            logger.info("getPortfolio exec completed for portfolioId {}",id);
            return new BaseResponse(portfolio);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new BaseResponse(e);
        }
    }

    @RequestMapping(value = "/users/{userId}/portfolios/{id}",method = RequestMethod.PATCH,consumes = MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse updatePortfolio(@PathVariable(value = "userId") Long userId,@PathVariable("id") Long id,@RequestBody UpdatePortfolioRequest request){
        try {
            logger.info("updatePortfolio exec started for portfolioId   {}",id);

            Portfolio portfolio = portfolioService.updatePortfolio(userId,id,request);
            logger.info("updatePortfolio exec completed for portfolioId {}",id);
            return new BaseResponse(portfolio);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new BaseResponse(e);
        }
    }
    @RequestMapping(value = "/users/{userId}/portfolios/{id}",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse deletePortfolio(@PathVariable(value = "userId") Long userId,@PathVariable("id") Long id){
        try {
            logger.info("deletePortfolio exec started for portfolioId   {}",id);


            Portfolio portfolio = portfolioService.deletePortfolio(userId,id);
            logger.info("deletePortfolio exec completed for portfolioId {}",id);
            return new BaseResponse(portfolio);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new BaseResponse(e);
        }
    }
}
