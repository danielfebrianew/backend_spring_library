package com.tutorial.spring_library.service;

import com.tutorial.spring_library.exception.BadRequestException;
import com.tutorial.spring_library.exception.ResourceNotFoundException;
import com.tutorial.spring_library.model.Book;
import com.tutorial.spring_library.model.Loan;
import com.tutorial.spring_library.repository.BookRepository;
import com.tutorial.spring_library.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;

    // --- CREATE ---
    public Loan requestLoan(UUID userId, UUID bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + bookId));

        if (book.getAvailableCopies() <= 0) {
            throw new BadRequestException("Book is currently unavailable");
        }

        Loan loan = new Loan();
        loan.setUserId(userId);
        loan.setBookId(bookId);
        loan.setStatus("PENDING");
        loan.setRequestedAt(LocalDateTime.now());

        return loanRepository.save(loan);
    }

    // --- UPDATE ---
    @Transactional
    public void approveLoan(UUID loanId, UUID adminId, LocalDate dueDate) {
        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new ResourceNotFoundException("Loan not found with ID: " + loanId));

        if (!"PENDING".equals(loan.getStatus())) {
            throw new BadRequestException("Loan is not in PENDING status");
        }

        Book book = bookRepository.findById(loan.getBookId()).orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + loan.getBookId()));

        if (book.getAvailableCopies() <= 0) {
            throw new BadRequestException("Book is out of stock");
        }

        bookRepository.updateAvailableCopies(book.getId(), book.getAvailableCopies() - 1);

        loan.setStatus("APPROVED");
        loan.setApprovedBy(adminId);
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(dueDate);

        loanRepository.update(loan);
    }

    @Transactional
    public void returnLoan(UUID loanId) {
        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new ResourceNotFoundException("Loan not found with ID: " + loanId));

        if (!"APPROVED".equals(loan.getStatus())) {
            throw new BadRequestException("Loan is not in APPROVED status");
        }

        Book book = bookRepository.findById(loan.getBookId()).orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + loan.getBookId()));

        bookRepository.updateAvailableCopies(book.getId(), book.getAvailableCopies() + 1);

        loan.setStatus("RETURNED");
        loan.setReturnDate(LocalDate.now());

        loanRepository.update(loan);
    }

    // --- READ ---
    public List<Loan> getPendingLoans() {
        return loanRepository.findByStatus("PENDING");
    }

    public List<Loan> getUserLoanHistory(UUID userId) {
        return loanRepository.findByUserId(userId);
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }
}