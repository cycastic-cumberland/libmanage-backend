package com.cycastic.library_management.models.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookCreationRequest {
    private String title;
    private Collection<String> authors;
    private String isbn;
    private String capsuleUrl;
    private LocalDateTime publishedDate;
    private int inStorage;
}
