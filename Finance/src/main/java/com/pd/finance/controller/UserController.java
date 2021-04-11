package com.pd.finance.controller;

import com.pd.finance.exceptions.UserNotFoundException;
import com.pd.finance.model.user.User;
import com.pd.finance.persistence.UserRepository;
import com.pd.finance.service.SequenceGeneratorService;
import com.pd.finance.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @GetMapping("/users")
    public List<User> getAllEmployees() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity < User > getEmployeeById(@PathVariable(value = "id") Long userId)
            throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found for this id :: " + userId));
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/users")
    public User createEmployee(@Valid @RequestBody User user){
        user.setId(sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME));
        user.setPassword(UserUtils.getEncryptedPassword(user.getPassword()));
        return userRepository.save(user);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity <User> updateEmployee(@PathVariable(value = "id") Long userId,
                                                      @Valid @RequestBody User userDetaila) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found for this id :: " + userId));

        user.setUsername(userDetaila.getUsername());
        user.setEmail(userDetaila.getEmail());
        user.setMobile(userDetaila.getMobile());
        final User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/{id}")
    public Map< String,Boolean > deleteEmployee(@PathVariable(value = "id") Long userId)
            throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found for this id :: " + userId));

        userRepository.delete(user);
        Map < String, Boolean > response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }


}
