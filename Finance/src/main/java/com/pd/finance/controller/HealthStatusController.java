package com.pd.finance.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HealthStatusController {

    @RequestMapping("/ping")
    public String index() {
        return "Congratulations! your application is up and running!";
    }

}