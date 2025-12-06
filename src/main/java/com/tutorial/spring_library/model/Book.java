package com.tutorial.spring_library.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
