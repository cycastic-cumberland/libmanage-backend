package com.cycastic.library_management.models.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookUpdateRequest {
    private int id;
    private String title;
    private String capsuleUrl;
    private String isbn;
    private LocalDateTime publishedDate;
    private Integer inStorage;
}
