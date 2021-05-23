package com.pd.finance.controller;

import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.exceptions.UserNotFoundException;
import com.pd.finance.model.GlobalEquityQuery;
import com.pd.finance.model.UserEquityQuery;
import com.pd.finance.request.CreateGlobalEquityQueryRequest;
import com.pd.finance.response.BaseResponse;
import com.pd.finance.service.IGlobalEquityQueryService;
import com.pd.finance.service.IUserQueryService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

@RestController
@RequestMapping("/api/v1")
public class GlobalEquityQueryController {
    private static Logger logger = LoggerFactory.getLogger(GlobalEquityQueryController.class);

    @Autowired
    private IGlobalEquityQueryService globalEquityQueryService;

    @PostMapping("/equity/globalQueries")
    public ResponseEntity<?> createGlobalEquityQuery(@Valid @RequestBody CreateGlobalEquityQueryRequest createGlobalEquityQueryRequest) {

        try {

            GlobalEquityQuery globalEquityQueryFromDB = globalEquityQueryService.createGlobalEquityQuery(createGlobalEquityQueryRequest.getSearchRequest());
            return ResponseEntity.ok(globalEquityQueryFromDB);
        } catch (ServiceException e) {

            logger.error(e.getMessage(),e);
            return handleCreateException(e);
        }

    }

    @GetMapping("/equity/globalQueries/{id}")
    public ResponseEntity<GlobalEquityQuery> getGlobalQueryById(@PathVariable(value = "id") Long queryId) {
        try {
            GlobalEquityQuery globalEquityQuery = globalEquityQueryService.getGlobalEquityQueryById(queryId);
            if(globalEquityQuery!=null){

                return ResponseEntity.ok().body(globalEquityQuery);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

        } catch (ServiceException e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PatchMapping("/equity/globalQueries/{id}/ratings")
    public ResponseEntity<GlobalEquityQuery> updateGlobalQueryRatingById(@PathVariable(value = "id") Long queryId,@RequestParam(name = "numStars")@Min(1) @Max(5) Integer numStars) {
        try {
            GlobalEquityQuery globalEquityQuery = globalEquityQueryService.updateGlobalEquityQueryRatings(queryId,numStars);
            if(globalEquityQuery!=null){

                return ResponseEntity.ok().body(globalEquityQuery);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

        } catch (ServiceException e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PatchMapping("/equity/globalQueries/{id}/exec")
    public ResponseEntity<GlobalEquityQuery> updateGlobalQueryLastExecutionById(@PathVariable(value = "id") Long queryId ) {
        try {
            GlobalEquityQuery globalEquityQuery = globalEquityQueryService.updateGlobalEquityQueryLastExecTime(queryId);
            if(globalEquityQuery!=null){

                return ResponseEntity.ok().body(globalEquityQuery);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

        } catch (ServiceException e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }



    @NotNull
    protected ResponseEntity<?> handleCreateException(ServiceException e) {
        Throwable rootCause = ExceptionUtils.getRootCause(e);
        BaseResponse baseResponse = new BaseResponse(rootCause);


        if(ExceptionUtils.indexOfThrowable(e, DuplicateKeyException.class)>0){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
