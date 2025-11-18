package com.tutorial.spring_library.exception;

import com.tutorial.spring_library.dto.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFound(ResourceNotFoundException ex) {
        logger.warn("ResourceNotFoundException: {}", ex.getMessage());
        ApiResponse<Void> response = ApiResponse.error(404, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadRequest(BadRequestException ex) {
        logger.warn("BadRequestException: {}", ex.getMessage());
        ApiResponse<Void> response = ApiResponse.error(400, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleDataIntegrity(DataIntegrityViolationException ex) {
        String message = "Data integrity violation. This operation conflicts with existing data.";
        if (ex.getMessage() != null && (ex.getMessage().contains("unique constraint") || ex.getMessage().contains("duplicate key"))) {
            message = "Duplicate entry. A record with this username or email already exists.";
        }

        logger.warn("DataIntegrityViolation: {}. Client Message: {}", ex.getMessage(), message);
        ApiResponse<Void> response = ApiResponse.error(400, message);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadJson(HttpMessageNotReadableException ex) {
        logger.warn("Malformed JSON request: {}", ex.getMessage());
        ApiResponse<Void> response = ApiResponse.error(400, "Malformed JSON request. Please check the request body format.");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericError(Exception ex) {
        logger.error("An unexpected internal server error occurred", ex);

        ApiResponse<Void> response = ApiResponse.error(500, "An unexpected internal server error occurred.");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}