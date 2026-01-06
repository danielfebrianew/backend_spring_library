package com.tutorial.spring_library.controller;

import com.tutorial.spring_library.dto.ApiResponse;
import com.tutorial.spring_library.dto.book.BookDto;
import com.tutorial.spring_library.model.Book;
import com.tutorial.spring_library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<ApiResponse<List<BookDto>>> getAllBooks() {
        List<BookDto> books = bookService.getAllBooks().stream()
                .map(this::mapToDto)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(books));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<ApiResponse<BookDto>> getBookById(@PathVariable UUID id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(ApiResponse.success(mapToDto(book)));
    }

    @GetMapping("/isbn/{isbn}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<ApiResponse<BookDto>> getBookByIsbn(@PathVariable String isbn) {
        Book book = bookService.getBookByIsbn(isbn);
        return ResponseEntity.ok(ApiResponse.success(mapToDto(book)));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<ApiResponse<List<BookDto>>> searchBooks(@RequestParam String query) {
        List<BookDto> books = bookService.searchBooks(query).stream()
                .map(this::mapToDto)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(books));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<BookDto>> createBook(@RequestBody BookDto dto) {
        Book savedBook = bookService.createBook(mapToEntity(dto));
        return ResponseEntity.status(201).body(ApiResponse.created(mapToDto(savedBook)));
    }

    // --- UPDATE ---
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<BookDto>> updateBook(@PathVariable UUID id, @RequestBody BookDto dto) {
        bookService.updateBook(id, dto);
        return ResponseEntity.ok(ApiResponse.updated(dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteBook(@PathVariable UUID id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok(ApiResponse.deleted());
    }

    private BookDto mapToDto(Book book) {
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setIsbn(book.getIsbn());
        dto.setPublisher(book.getPublisher());
        dto.setPublicationYear(book.getPublicationYear());
        dto.setTotalCopies(book.getTotalCopies());
        dto.setAvailableCopies(book.getAvailableCopies());
        return dto;
    }

    private Book mapToEntity(BookDto dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        book.setPublisher(dto.getPublisher());
        book.setPublicationYear(dto.getPublicationYear());
        book.setTotalCopies(dto.getTotalCopies());
        book.setAvailableCopies(dto.getTotalCopies());
        return book;
    }
}