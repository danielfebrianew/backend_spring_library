package com.tutorial.spring_library.repository.mapper;

import com.tutorial.spring_library.model.Loan;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class LoanRowMapper implements RowMapper<Loan> {

    @Override
    public Loan mapRow(ResultSet rs, int rowNum) throws SQLException {
        Loan loan = new Loan();

        loan.setId(rs.getObject("id", UUID.class));
        loan.setUserId(rs.getObject("user_id", UUID.class));
        loan.setBookId(rs.getObject("book_id", UUID.class));
        loan.setStatus(rs.getString("status"));
        loan.setRequestedAt(rs.getObject("requested_at", LocalDateTime.class));
        loan.setLoanDate(rs.getObject("loan_date", LocalDate.class));
        loan.setDueDate(rs.getObject("due_date", LocalDate.class));
        loan.setReturnDate(rs.getObject("return_date", LocalDate.class));
        loan.setApprovedBy(rs.getObject("approved_by", UUID.class));

        return loan;
    }
}