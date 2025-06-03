package com.ayush.microservices.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeControllerApi {

    @GetMapping("/message")
    public String getMessage(@RequestHeader(value = "X-User-Role", required = false) String role) {
        if ("ROLE_ADMIN".equals(role)) {
            return "Welcome Admin!";
        } else if (role != null) {
            return "Welcome User with role: " + role;
        } else {
            return "Welcome Guest!";
        }
    }
}
