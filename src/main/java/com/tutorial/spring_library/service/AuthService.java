package com.tutorial.spring_library.service;

import com.tutorial.spring_library.exception.BadRequestException;
import com.tutorial.spring_library.model.User;
import com.tutorial.spring_library.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String login(String username, String password) {
        User user = userService.getUserByUsername(username);

        boolean isPasswordMatch = passwordEncoder.matches(password, user.getPassword());

        if (!isPasswordMatch) {
            throw new BadRequestException("Invalid username or password");
        }

        return jwtUtil.generateToken(
                user.getUsername(),
                user.getRole(),
                user.getId().toString()
        );
    }
}