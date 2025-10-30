package com.ayush.microservices.controller;

import com.ayush.microservices.dto.APIResponse;
import com.ayush.microservices.dto.LoginDto;
import com.ayush.microservices.dto.UpdatePasswordDto;
import com.ayush.microservices.dto.UserDto;
import com.ayush.microservices.entity.User;
import com.ayush.microservices.repository.UserRepository;
import com.ayush.microservices.service.AuthService;
import com.ayush.microservices.service.JwtService;
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
        APIResponse<String> response = authService.register(dto);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register/admin")
    public ResponseEntity<APIResponse<String>> registerAdmin(@Valid @RequestBody UserDto dto) {
        dto.setRole("ROLE_ADMIN");
        APIResponse<String> response = authService.register(dto);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }


    @PutMapping("/update-password")
    public ResponseEntity<APIResponse<String>> updatePassword(@Valid @RequestBody UpdatePasswordDto updatePasswordDto) {
        APIResponse<String> response = authService.setNewPassword(updatePasswordDto);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }


    @PostMapping("/login")
    public ResponseEntity<APIResponse<String>> loginCheck(@Valid @RequestBody LoginDto loginDto) {
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
                response.setData(jwtToken);  // return JWT
                return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.setMessage("Failed");
        response.setStatus(401);
        response.setData("Un-Authorized Access");
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }

    @GetMapping("/get-user")
    public User getUser(@RequestParam String username) {
        return userRepository.findByUsername(username);
    }

}
