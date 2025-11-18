package com.tutorial.spring_library.repository;

import com.tutorial.spring_library.model.Loan;
import com.tutorial.spring_library.repository.mapper.LoanRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class LoanRepository {

    private final JdbcTemplate jdbcTemplate;

    public Loan save(Loan loan) {
        String sql = "INSERT INTO loans (user_id, book_id, status, requested_at) " +
                "VALUES (?, ?, ?, ?) " +
                "RETURNING *";

        return jdbcTemplate.queryForObject(sql, new LoanRowMapper(),
                loan.getUserId(),
                loan.getBookId(),
                loan.getStatus(),
                loan.getRequestedAt()
        );
    }

    public void update(Loan loan) {
        String sql = "UPDATE loans SET status = ?, loan_date = ?, due_date = ?, " +
                "return_date = ?, approved_by = ? " +
                "WHERE id = ?";

        jdbcTemplate.update(sql,
                loan.getStatus(),
                loan.getLoanDate(),
                loan.getDueDate(),
                loan.getReturnDate(),
                loan.getApprovedBy(),
                loan.getId()
        );
    }

    public Optional<Loan> findById(UUID id) {
        String sql = "SELECT * FROM loans WHERE id = ?";
        try {
            Loan loan = jdbcTemplate.queryForObject(sql, new LoanRowMapper(), id);
            return Optional.ofNullable(loan);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Loan> findAll() {
        String sql = "SELECT * FROM loans ORDER BY requested_at DESC";
        return jdbcTemplate.query(sql, new LoanRowMapper());
    }

    public List<Loan> findByUserId(UUID userId) {
        String sql = "SELECT * FROM loans WHERE user_id = ? ORDER BY requested_at DESC";
        return jdbcTemplate.query(sql, new LoanRowMapper(), userId);
    }

    public List<Loan> findByStatus(String status) {
        String sql = "SELECT * FROM loans WHERE status = ? ORDER BY requested_at";
        return jdbcTemplate.query(sql, new LoanRowMapper(), status);
    }

    public List<Loan> findActiveLoansByBookId(UUID bookId) {
        String sql = "SELECT * FROM loans WHERE book_id = ? AND status = 'APPROVED'";
        return jdbcTemplate.query(sql, new LoanRowMapper(), bookId);
    }
}