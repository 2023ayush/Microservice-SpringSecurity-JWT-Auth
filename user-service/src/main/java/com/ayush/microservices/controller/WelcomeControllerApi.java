package com.ayush.microservices.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeControllerApi {

        @GetMapping("/message")
        public String getMessage() {
            return "Welcome To ADMIN Portal";
        }


//    @GetMapping("/admin")
//    @PreAuthorize("hasRole('ADMIN')")
//    public String getAdminMessage() {
//        return "Welcome ADMIN";
//    }
//
//    @GetMapping("/user")
//    @PreAuthorize("hasRole('USER')")
//    public String getUserMessage() {
//        return "Welcome USER";
//    }
}



