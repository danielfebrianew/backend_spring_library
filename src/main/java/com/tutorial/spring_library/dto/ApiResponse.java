package com.tutorial.spring_library.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private int status;
    private boolean success;
    private String message;
    private T data;
    private String timestamp;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .status(200)
                .message("Data retrieved successfully")
                .data(data)
                .timestamp(LocalDateTime.now().format(FORMATTER))
                .build();
    }

    public static <T> ApiResponse<T> created(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .status(201)
                .message("Created successfully")
                .data(data)
                .timestamp(LocalDateTime.now().format(FORMATTER))
                .build();
    }

    public static <T> ApiResponse<T> updated(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .status(200)
                .message("Updated successfully")
                .data(data)
                .timestamp(LocalDateTime.now().format(FORMATTER))
                .build();
    }

    public static ApiResponse<Void> deleted() {
        return ApiResponse.<Void>builder()
                .success(true)
                .status(200)
                .message("Deleted successfully")
                .data(null)
                .timestamp(LocalDateTime.now().format(FORMATTER))
                .build();
    }

    public static <T> ApiResponse<T> error(int status, String message) {
        return ApiResponse.<T>builder()
                .success(false)
                .status(status)
                .message(message)
                .data(null)
                .timestamp(LocalDateTime.now().format(FORMATTER))
                .build();
    }
}