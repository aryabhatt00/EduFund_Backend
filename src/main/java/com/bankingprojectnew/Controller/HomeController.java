package com.bankingprojectnew.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/test")
    public String home() {
        return "Spring Boot is working!";
    }
}
