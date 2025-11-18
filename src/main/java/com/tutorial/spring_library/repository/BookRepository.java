package com.tutorial.spring_library.repository;

import com.tutorial.spring_library.model.Book;
import com.tutorial.spring_library.repository.mapper.BookRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class BookRepository {

    private final JdbcTemplate jdbcTemplate;

    public Book save(Book book) {
        String sql = "INSERT INTO books " +
                "(title, author, isbn, publisher, publication_year, total_copies, available_copies) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                "RETURNING *";

        return jdbcTemplate.queryForObject(sql, new BookRowMapper(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getPublisher(),
                book.getPublicationYear(),
                book.getTotalCopies(),
                book.getAvailableCopies()
        );
    }

    public Optional<Book> findById(UUID id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try {
            Book book = jdbcTemplate.queryForObject(sql, new BookRowMapper(), id);
            return Optional.ofNullable(book);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Book> findByIsbn(String isbn) {
        String sql = "SELECT * FROM books WHERE isbn = ?";
        try {
            Book book = jdbcTemplate.queryForObject(sql, new BookRowMapper(), isbn);
            return Optional.ofNullable(book);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Book> findAll() {
        String sql = "SELECT * FROM books ORDER BY title";
        return jdbcTemplate.query(sql, new BookRowMapper());
    }

    public List<Book> searchBooks(String keyword) {
        String sql = "SELECT * FROM books WHERE title ILIKE ? OR author ILIKE ?";
        String param = "%" + keyword + "%";
        return jdbcTemplate.query(sql, new BookRowMapper(), param, param);
    }

    public void update(Book book) {
        String sql = "UPDATE books SET title = ?, author = ?, isbn = ?, publisher = ?, " +
                "publication_year = ?, total_copies = ?, available_copies = ? " +
                "WHERE id = ?";

        jdbcTemplate.update(sql,
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getPublisher(),
                book.getPublicationYear(),
                book.getTotalCopies(),
                book.getAvailableCopies(),
                book.getId()
        );
    }

    public void updateAvailableCopies(UUID id, int newCopies) {
        String sql = "UPDATE books SET available_copies = ? WHERE id = ?";
        jdbcTemplate.update(sql, newCopies, id);
    }

    public void deleteById(UUID id) {
        String sql = "DELETE FROM books WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}