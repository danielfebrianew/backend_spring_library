package com.tutorial.spring_library.dto;

public record RegisterRequest(String username, String password, String fullName, String email) {}