package com.ayush.microservices;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashedPasswordGenerator {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "admin123";
        String hashedPassword = encoder.encode(rawPassword);
        System.out.println("Hashed password: " + hashedPassword);
    }
}
