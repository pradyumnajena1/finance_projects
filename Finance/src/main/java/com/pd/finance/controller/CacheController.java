package com.pd.finance.controller;

import com.pd.finance.htmlscrapper.equity.EquityTechnicalDetailsFactory;
import com.pd.finance.request.ClearCacheRequest;
import com.pd.finance.response.BaseResponse;
import com.pd.finance.service.ICacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheController {

    private static final Logger logger = LoggerFactory.getLogger(CacheController.class);
    @Autowired
    private ICacheService cacheService;

    @RequestMapping(value="/clear_caches", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse clearCaches(@RequestBody ClearCacheRequest request) {

        try {
            StringBuilder sb  = new StringBuilder();

            if(request.isClearDocumentCache()){
                cacheService.clearDocumentCache();
                sb.append("Document Cache cleared! ");
            }
            if(request.isClearEnrichedEquityCache()){
                cacheService.clearEnrichedEquityCache();
                sb.append("Enriched Equity Cache cleared! ");
            }

            return new BaseResponse(sb.toString());
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new BaseResponse(e);
        }
    }
}
