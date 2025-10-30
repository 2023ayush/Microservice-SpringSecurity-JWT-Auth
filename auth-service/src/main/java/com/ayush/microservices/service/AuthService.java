package com.ayush.microservices.service;

import com.ayush.microservices.dto.APIResponse;
import com.ayush.microservices.dto.UpdatePasswordDto;
import com.ayush.microservices.dto.UserDto;
import com.ayush.microservices.entity.User;
import com.ayush.microservices.exception.CustomException;
import com.ayush.microservices.exception.ResourceNotFoundException;
import com.ayush.microservices.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ Registration Logic
    public APIResponse<String> register(UserDto dto) {
        logger.info("Attempting to register user: {}", dto.getUsername());

        if (userRepository.existsByUsername(dto.getUsername())) {
            logger.warn("Registration failed: Username '{}' already exists", dto.getUsername());
            throw new CustomException("User with username '" + dto.getUsername() + "' already exists.");
        }

        if (userRepository.existsByEmail(dto.getEmail())) {
            logger.warn("Registration failed: Email '{}' already exists", dto.getEmail());
            throw new CustomException("User with email '" + dto.getEmail() + "' already exists.");
        }

        User user = new User();
        BeanUtils.copyProperties(dto, user);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        userRepository.save(user);
        logger.info("User '{}' registered successfully with role '{}'", dto.getUsername(), dto.getRole());

        String roleMessage = "User is registered successfully";
        if ("ROLE_ADMIN".equalsIgnoreCase(dto.getRole())) {
            roleMessage = "Admin is registered successfully";
        }

        APIResponse<String> response = new APIResponse<>();
        response.setStatus(201);
        response.setMessage("Registration Successful");
        response.setData(roleMessage);
        return response;
    }

    // ✅ Password Update Logic
    public APIResponse<String> setNewPassword(UpdatePasswordDto updatePasswordDto) {
        logger.info("Password update requested for username: {}", updatePasswordDto.getUsername());

        User user = userRepository.findByEmail(updatePasswordDto.getEmail());
        if (user == null) {
            logger.warn("Password update failed: User not found with email {}", updatePasswordDto.getEmail());
            throw new ResourceNotFoundException("User not found with email: " + updatePasswordDto.getEmail());
        }

        if (!userRepository.existsByUsername(updatePasswordDto.getUsername())) {
            logger.warn("Password update failed: User not found with username {}", updatePasswordDto.getUsername());
            throw new ResourceNotFoundException("User not found with username: " + updatePasswordDto.getUsername());
        }

        if (!BCrypt.checkpw(updatePasswordDto.getOldPassword(), user.getPassword())) {
            logger.warn("Password update failed: Old password incorrect for username {}", updatePasswordDto.getUsername());
            throw new CustomException("Old password is incorrect. Please try again.");
        }

        user.setPassword(passwordEncoder.encode(updatePasswordDto.getNewPassword()));
        userRepository.save(user);
        logger.info("Password updated successfully for username: {}", user.getUsername());

        APIResponse<String> response = new APIResponse<>();
        response.setStatus(200);
        response.setMessage("Password updated successfully");
        response.setData("Password change completed for user: " + user.getUsername());
        return response;
    }
}
