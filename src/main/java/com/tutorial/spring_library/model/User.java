package com.tutorial.spring_library.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class User {
    private UUID id;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
