package com.tutorial.spring_library.dto;

import java.time.LocalDate;

public record LoanApproveRequest(LocalDate dueDate) {}