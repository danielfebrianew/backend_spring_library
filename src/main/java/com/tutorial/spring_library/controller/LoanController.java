package com.tutorial.spring_library.controller;

import com.tutorial.spring_library.dto.ApiResponse;
import com.tutorial.spring_library.dto.LoanApproveRequest;
import com.tutorial.spring_library.dto.LoanDto;
import com.tutorial.spring_library.model.Loan;
import com.tutorial.spring_library.model.User;
import com.tutorial.spring_library.service.LoanService;
import com.tutorial.spring_library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;
    private final UserService userService;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userService.getUserByUsername(username);
    }

    @PostMapping("/request/{bookId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<ApiResponse<LoanDto>> requestLoan(@PathVariable UUID bookId) {
        User currentUser = getCurrentUser();
        Loan loan = loanService.requestLoan(currentUser.getId(), bookId);
        return ResponseEntity.status(201).body(ApiResponse.created(mapToDto(loan)));
    }

    @GetMapping("/my-loans")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<ApiResponse<List<LoanDto>>> getMyLoans() {
        User currentUser = getCurrentUser();
        List<LoanDto> loans = loanService.getUserLoanHistory(currentUser.getId()).stream()
                .map(this::mapToDto)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(loans));
    }

    @GetMapping("/pending")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<List<LoanDto>>> getPendingLoans() {
        List<LoanDto> loans = loanService.getPendingLoans().stream()
                .map(this::mapToDto)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(loans));
    }

    @PutMapping("/{loanId}/approve")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<Void>> approveLoan(@PathVariable UUID loanId, @RequestBody LoanApproveRequest request) {
        User admin = getCurrentUser();
        loanService.approveLoan(loanId, admin.getId(), request.dueDate());
        return ResponseEntity.ok(ApiResponse.updated(null));
    }

    @PutMapping("/{loanId}/return")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<Void>> returnLoan(@PathVariable UUID loanId) {
        loanService.returnLoan(loanId);
        return ResponseEntity.ok(ApiResponse.updated(null));
    }

    private LoanDto mapToDto(Loan loan) {
        LoanDto dto = new LoanDto();
        dto.setId(loan.getId());
        dto.setUserId(loan.getUserId());
        dto.setBookId(loan.getBookId());
        dto.setStatus(loan.getStatus());
        dto.setRequestedAt(loan.getRequestedAt());
        dto.setLoanDate(loan.getLoanDate());
        dto.setDueDate(loan.getDueDate());
        dto.setReturnDate(loan.getReturnDate());
        dto.setApprovedBy(loan.getApprovedBy());
        return dto;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<List<LoanDto>>> getAllLoans() {
        List<LoanDto> loans = loanService.getAllLoans().stream()
                .map(this::mapToDto)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(loans));
    }
}