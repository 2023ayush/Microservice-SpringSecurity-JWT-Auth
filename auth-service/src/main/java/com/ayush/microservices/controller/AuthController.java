package com.ayush.microservices.controller;

import com.ayush.microservices.dto.APIResponse;
import com.ayush.microservices.dto.LoginDto;
import com.ayush.microservices.dto.UpdatePasswordDto;
import com.ayush.microservices.dto.UserDto;
import com.ayush.microservices.entity.User;
import com.ayush.microservices.repository.UserRepository;
import com.ayush.microservices.service.AuthService;
import com.ayush.microservices.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authManager;

    AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register/user")
    public ResponseEntity<APIResponse<String>> register(@Valid @RequestBody UserDto dto) {
        logger.info("Received request to register user: {}", dto.getUsername());
        APIResponse<String> response = authService.register(dto);
        logger.info("Registration response for user {}: {}", dto.getUsername(), response.getMessage());
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register/admin")
    public ResponseEntity<APIResponse<String>> registerAdmin(@Valid @RequestBody UserDto dto) {
        dto.setRole("ROLE_ADMIN");
        logger.info("Received request to register admin: {}", dto.getUsername());
        APIResponse<String> response = authService.register(dto);
        logger.info("Registration response for admin {}: {}", dto.getUsername(), response.getMessage());
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }

    @PutMapping("/update-password")
    public ResponseEntity<APIResponse<String>> updatePassword(@Valid @RequestBody UpdatePasswordDto updatePasswordDto) {
        logger.info("Received request to update password for username: {}", updatePasswordDto.getUsername());
        APIResponse<String> response = authService.setNewPassword(updatePasswordDto);
        logger.info("Password update response for username {}: {}", updatePasswordDto.getUsername(), response.getMessage());
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }

    @PostMapping("/login")
    public ResponseEntity<APIResponse<String>> loginCheck(@Valid @RequestBody LoginDto loginDto) {
        logger.info("Login attempt for username: {}", loginDto.getUsername());
        APIResponse<String> response = new APIResponse<>();

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        try {
            Authentication authenticate = authManager.authenticate(token);

            if(authenticate.isAuthenticated()) {
                String jwtToken = jwtService.generateToken(
                        loginDto.getUsername(),
                        authenticate.getAuthorities().iterator().next().getAuthority()
                );

                response.setMessage("Login Successful");
                response.setStatus(200);
                response.setData(jwtToken);
                logger.info("Login successful for username: {}", loginDto.getUsername());
                return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
            }
        } catch (Exception e) {
            logger.error("Login failed for username {}: {}", loginDto.getUsername(), e.getMessage());
        }

        response.setMessage("Failed");
        response.setStatus(401);
        response.setData("Un-Authorized Access");
        logger.warn("Unauthorized login attempt for username: {}", loginDto.getUsername());
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }

    @GetMapping("/get-user")
    public User getUser(@RequestParam String username) {
        logger.info("Get user request received for username: {}", username);
        return userRepository.findByUsername(username);
    }
}
