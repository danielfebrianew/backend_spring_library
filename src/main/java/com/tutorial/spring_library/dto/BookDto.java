package com.tutorial.spring_library.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDto {
    private UUID id;
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private Integer publicationYear;
    private Integer totalCopies;
    private Integer availableCopies;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}