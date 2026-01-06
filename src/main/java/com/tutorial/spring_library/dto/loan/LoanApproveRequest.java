package com.tutorial.spring_library.dto.loan;

import java.time.LocalDate;

public record LoanApproveRequest(LocalDate dueDate) {}