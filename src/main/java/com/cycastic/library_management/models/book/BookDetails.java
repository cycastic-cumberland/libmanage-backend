package com.cycastic.library_management.models.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDetails {
    private int id;
    private String title;
    private String capsuleUrl;
    private List<String> authors;
    private String isbn;
    private LocalDateTime publishedDate;
    private int inStorage;
}
