package com.tutorial.spring_library.model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {
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
