package com.pd.finance.controller;

import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.exceptions.UserNotFoundException;
import com.pd.finance.model.user.User;
import com.pd.finance.persistence.UserRepository;
import com.pd.finance.service.SequenceGeneratorService;
import com.pd.finance.service.user.IUserService;
import com.pd.finance.utils.UserUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private IUserService userService;


    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> allUsers = userService.getAllUsers();
            return ResponseEntity.ok().body(allUsers);
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId) {
        try {
            User user = userService.getUser(userId);
            return ResponseEntity.ok().body(user);

        } catch (ServiceException e) {

           if(ExceptionUtils.getRootCause(e) instanceof UserNotFoundException){
               return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
           }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {

        try {
            User userFromDb = userService.createUser(user);
            return ResponseEntity.ok(userFromDb);
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId,
                                           @Valid @RequestBody User userDetails) throws UserNotFoundException {
        try {
            User updatedUser = userService.updateUser(userId, userDetails);
            return ResponseEntity.ok(updatedUser);
        } catch (ServiceException e) {
            if(ExceptionUtils.getRootCause(e) instanceof UserNotFoundException){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> deleteEmployee(@PathVariable(value = "id") Long userId) {
        try {
            User deletedUser = userService.deleteUser(userId);
            return ResponseEntity.ok(deletedUser);
        } catch (ServiceException e) {
            if(ExceptionUtils.getRootCause(e) instanceof UserNotFoundException){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }


}
