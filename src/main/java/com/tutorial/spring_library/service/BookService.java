package com.tutorial.spring_library.service;

import com.tutorial.spring_library.exception.ResourceNotFoundException;
import com.tutorial.spring_library.model.Book;
import com.tutorial.spring_library.dto.book.BookDto; // Import DTO
import com.tutorial.spring_library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    // --- CREATE ---
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    // --- READ ---
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(UUID id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + id));
    }

    public List<Book> searchBooks(String keyword) {
        return bookRepository.searchBooks(keyword);
    }

    public Book getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ISBN: " + isbn));
    }

    // --- UPDATE ---
    public void updateBook(UUID id, BookDto dto) {
        Book existingBook = getBookById(id);

        int booksOnLoan = existingBook.getTotalCopies() - existingBook.getAvailableCopies();

        existingBook.setTitle(dto.getTitle());
        existingBook.setAuthor(dto.getAuthor());
        existingBook.setIsbn(dto.getIsbn());
        existingBook.setPublisher(dto.getPublisher());
        existingBook.setPublicationYear(dto.getPublicationYear());
        existingBook.setTotalCopies(dto.getTotalCopies());
        existingBook.setAvailableCopies(dto.getTotalCopies() - booksOnLoan);

        bookRepository.update(existingBook);
    }

    // --- DELETE ---
    public void deleteBook(UUID id) {
        if (bookRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Book not found with ID: " + id);
        }
        bookRepository.deleteById(id);
    }
}