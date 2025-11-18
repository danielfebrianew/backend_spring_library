package com.tutorial.spring_library.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoanDto {
    private UUID id;
    private UUID userId;
    private UUID bookId;
    private String status;
    private LocalDateTime requestedAt;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private UUID approvedBy;
}