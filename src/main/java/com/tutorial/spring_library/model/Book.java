package com.tutorial.spring_library.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Book {
    private UUID id;
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private int publicationYear;
    private int totalCopies;
    private int availableCopies;
}
