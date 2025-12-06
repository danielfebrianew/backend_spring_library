package com.tutorial.spring_library.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Username wajib diisi")
        String username,

        @NotBlank(message = "Password wajib diisi")
        String password
) {
}