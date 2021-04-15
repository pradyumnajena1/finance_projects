package com.pd.finance.controller;

import com.pd.finance.exceptions.DuplicateEntityException;
import com.pd.finance.exceptions.QueryNotFoundException;
import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.exceptions.UserNotFoundException;
import com.pd.finance.model.Equity;
import com.pd.finance.model.UserEquityQuery;
import com.pd.finance.model.user.User;
import com.pd.finance.response.BaseResponse;
import com.pd.finance.response.EquitySearchResponse;
import com.pd.finance.service.IUserQueryService;
import com.pd.finance.service.user.IUserService;
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
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserQueryController {

    private static Logger logger = LoggerFactory.getLogger(UserQueryController.class);

    @Autowired
    private IUserQueryService userQueryService;

    @PostMapping("/users/{userId}/queries")
    public ResponseEntity<?> createUserQuery(@PathVariable("userId") long userId,@Valid @RequestBody UserEquityQuery userEquityQuery) {

        try {
            userEquityQuery.setUserId(userId);
            userEquityQuery.setCreatedDate(new Date());
            userEquityQuery.setUpdatedDate(new Date());
            UserEquityQuery userEquityQueryFromDB = userQueryService.createUserEquityQuery(userEquityQuery);
            return ResponseEntity.ok(userEquityQueryFromDB);
        } catch (ServiceException e) {

            logger.error(e.getMessage(),e);
            return handleCreateException(e);
        }

    }

    @PatchMapping("/users/{userId}/queries/{id}")
    public ResponseEntity<?> updateUserQuery(@PathVariable(value = "userId") Long userId,@PathVariable(value = "id") Long id,
                                           @Valid @RequestBody UserEquityQuery userEquityQueryDetails) throws UserNotFoundException {
        try {
            UserEquityQuery updatedUserEquityQuery = userQueryService.updateUserEquityQuery(userId,id, userEquityQueryDetails);
            return ResponseEntity.ok(updatedUserEquityQuery);
        } catch (ServiceException e) {
            return handleUpdateException(e);
        }

    }

    @NotNull
    protected ResponseEntity<?> handleUpdateException(ServiceException e) {
        Throwable rootCause = ExceptionUtils.getRootCause(e);
        BaseResponse baseResponse = new BaseResponse(rootCause);
        if(ExceptionUtils.indexOfThrowable(e, UserNotFoundException.class)>0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(baseResponse);
        }
        if(ExceptionUtils.indexOfThrowable(e, QueryNotFoundException.class)>0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(baseResponse);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @NotNull
    protected ResponseEntity<?> handleCreateException(ServiceException e) {
        Throwable rootCause = ExceptionUtils.getRootCause(e);
        BaseResponse baseResponse = new BaseResponse(rootCause);

        if(ExceptionUtils.indexOfThrowable(e, UserNotFoundException.class)>0){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse);
        }
        if(ExceptionUtils.indexOfThrowable(e, DuplicateKeyException.class)>0){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/users/{userId}/queries/{id}")
    public ResponseEntity<UserEquityQuery> getUserQueryById(@PathVariable(value = "userId") Long userId,@PathVariable(value = "id") Long userQueryId) {
        try {
            UserEquityQuery userEquityQuery = userQueryService.getUserEquityQuery(userId,userQueryId);
            if(userEquityQuery!=null){

                return ResponseEntity.ok().body(userEquityQuery);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

        } catch (ServiceException e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @DeleteMapping("/users/{userId}/queries/{id}")
    public ResponseEntity<?> deleteUserQueryById(@PathVariable(value = "userId") Long userId,@PathVariable(value = "id") Long userQueryId) {
        try {
            UserEquityQuery userEquityQuery = userQueryService.deleteUserEquityQuery(userId,userQueryId);


            return ResponseEntity.ok().body(userEquityQuery);


        } catch (ServiceException e) {
                Throwable rootCause = ExceptionUtils.getRootCause(e);
                BaseResponse baseResponse = new BaseResponse(rootCause);

                if(ExceptionUtils.indexOfThrowable(e, QueryNotFoundException.class)>0){

                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse);
                }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/users/{userId}/queries/{id}/execute")
    public ResponseEntity<?> executeUserQueryById(@PathVariable(value = "userId") Long userId,@PathVariable(value = "id") Long userQueryId) {
        try {
            List<Equity> equities = userQueryService.executeUserEquityQuery(userId,userQueryId);
            if(equities!=null){
                EquitySearchResponse searchResponse = new EquitySearchResponse();
                searchResponse.setEquities(equities);
                return ResponseEntity.ok().body(searchResponse);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

        } catch (ServiceException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            BaseResponse baseResponse = new BaseResponse(rootCause);

            if(ExceptionUtils.indexOfThrowable(e, QueryNotFoundException.class)>0){

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse);
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

}
