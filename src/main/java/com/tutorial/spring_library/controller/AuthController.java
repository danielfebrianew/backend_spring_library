package com.tutorial.spring_library.controller;

import com.tutorial.spring_library.dto.*;
import com.tutorial.spring_library.dto.auth.AuthResponse;
import com.tutorial.spring_library.dto.auth.LoginRequest;
import com.tutorial.spring_library.dto.auth.RegisterRequest;
import com.tutorial.spring_library.dto.user.UserDto;
import com.tutorial.spring_library.model.User;
import com.tutorial.spring_library.service.AuthService;
import com.tutorial.spring_library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto>> register(@RequestBody RegisterRequest request) {
        User user = new User();
        user.setUsername(request.username());
        user.setPassword(request.password());
        user.setFullName(request.fullName());
        user.setEmail(request.email());

        User savedUser = userService.registerUser(user);

        return ResponseEntity.status(201).body(ApiResponse.created(mapToDto(savedUser)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        String token = authService.login(request.username(), request.password());

        return ResponseEntity.ok(ApiResponse.success(new AuthResponse(token)));
    }

    private UserDto mapToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }
}