package com.pd.finance.controller;

import com.pd.finance.model.Equity;
import com.pd.finance.request.EquitySearchByNameRequest;
import com.pd.finance.request.EquitySearchRequest;
import com.pd.finance.response.BaseResponse;
import com.pd.finance.response.EquitySearchResponse;
import com.pd.finance.service.EquitySearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class EquitySearchController {
    private Logger logger = LoggerFactory.getLogger(EquitySearchController.class);
    @Autowired
    private EquitySearchService searchService;

    @RequestMapping(value = "/equities/search",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse search(@RequestBody EquitySearchRequest request) {

        try {
            List<FieldError> fieldErrors = request.validate();
            if(fieldErrors!=null && fieldErrors.size()>0){
                BaseResponse baseResponse = getValidationErrorResponse(fieldErrors);
                return baseResponse;
            }
            List<Equity> equities =  searchService.search(request);
            EquitySearchResponse searchResponse = new EquitySearchResponse(equities);

            return new BaseResponse(searchResponse);
        } catch (Exception e) {
            logger.error("Failed to process Gainers",e);
            return  new BaseResponse(e);
        }

    }

    @RequestMapping(value = "/equities/search/name",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse searchByName(@RequestBody EquitySearchByNameRequest request) {

        try {
            List<String> fieldErrors = request.getValidationErrors();
            if(fieldErrors!=null && fieldErrors.size()>0){
                BaseResponse baseResponse = getValidationErrorResponse(fieldErrors);
                return baseResponse;
            }
            List<Equity> equities =  searchService.searchByName(request);
            EquitySearchResponse searchResponse = new EquitySearchResponse(equities);

            return new BaseResponse(searchResponse);
        } catch (Exception e) {
            logger.error("Failed to process Gainers",e);
            return  new BaseResponse(e);
        }

    }



    private BaseResponse getValidationErrorResponse(List<?> fieldErrors) {
        BaseResponse baseResponse = new BaseResponse(fieldErrors);
        baseResponse.setSuccess(false);
        baseResponse.setErrorMessage("request validation errors");
        return baseResponse;
    }
}
