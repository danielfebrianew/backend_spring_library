package com.tutorial.spring_library.dto.book;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDto {
    private UUID id;

    @NotBlank(message = "Judul buku tidak boleh kosong")
    private String title;

    @NotBlank(message = "Author tidak boleh kosong")
    private String author;

    @NotBlank(message = "ISBN tidak boleh kosong")
    private String isbn;
    private String publisher;
    private Integer publicationYear;

    @NotNull(message = "Total copies tidak boleh kosong")
    @Min(value = 1, message = "Total copies harus minimal 1")
    private Integer totalCopies;

    private Integer availableCopies;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}