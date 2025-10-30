package com.ayush.microservices.service;

import com.ayush.microservices.dto.APIResponse;
import com.ayush.microservices.dto.UpdatePasswordDto;
import com.ayush.microservices.dto.UserDto;
import com.ayush.microservices.entity.User;
import com.ayush.microservices.exception.CustomException;
import com.ayush.microservices.exception.ResourceNotFoundException;
import com.ayush.microservices.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ Registration Logic with Exception Handling
    public APIResponse<String> register(UserDto dto) {

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new CustomException("User with username '" + dto.getUsername() + "' already exists.");
        }

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new CustomException("User with email '" + dto.getEmail() + "' already exists.");
        }

        User user = new User();
        BeanUtils.copyProperties(dto, user);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        userRepository.save(user);

        String role = dto.getRole();
        String roleMessage = "User is registered successfully";
        if ("ROLE_ADMIN".equalsIgnoreCase(role)) {
            roleMessage = "Admin is registered successfully";
        }

        APIResponse<String> response = new APIResponse<>();
        response.setStatus(201);
        response.setMessage("Registration Successful");
        response.setData(roleMessage);
        return response;
    }

    // ✅ Password Update Logic with Exception Handling
    public APIResponse<String> setNewPassword(UpdatePasswordDto updatePasswordDto) {

        User user = userRepository.findByEmail(updatePasswordDto.getEmail());
        if (user == null) {
            throw new ResourceNotFoundException("User not found with email: " + updatePasswordDto.getEmail());
        }

        if (!userRepository.existsByUsername(updatePasswordDto.getUsername())) {
            throw new ResourceNotFoundException("User not found with username: " + updatePasswordDto.getUsername());
        }

        if (!BCrypt.checkpw(updatePasswordDto.getOldPassword(), user.getPassword())) {
            throw new CustomException("Old password is incorrect. Please try again.");
        }

        user.setPassword(passwordEncoder.encode(updatePasswordDto.getNewPassword()));
        userRepository.save(user);

        APIResponse<String> response = new APIResponse<>();
        response.setStatus(200);
        response.setMessage("Password updated successfully");
        response.setData("Password change completed for user: " + user.getUsername());
        return response;
    }
}
