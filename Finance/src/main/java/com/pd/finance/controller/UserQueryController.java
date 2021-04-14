package com.pd.finance.controller;

import com.pd.finance.exceptions.DuplicateEntityException;
import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.exceptions.UserNotFoundException;
import com.pd.finance.model.UserEquityQuery;
import com.pd.finance.model.user.User;
import com.pd.finance.response.BaseResponse;
import com.pd.finance.service.IUserQueryService;
import com.pd.finance.service.user.IUserService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

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
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            if(rootCause instanceof UserNotFoundException){
                BaseResponse baseResponse = new BaseResponse(rootCause);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/users/{userId}/queries/{id}")
    public ResponseEntity<UserEquityQuery> getUserQueryById(@PathVariable(value = "userId") Long userId,@PathVariable(value = "id") Long userQueryId) {
        try {
            UserEquityQuery userEquityQuery = userQueryService.getUserEquityQuery(userId,userQueryId);
            return ResponseEntity.ok().body(userEquityQuery);

        } catch (ServiceException e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

}
