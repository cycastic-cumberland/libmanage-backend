package com.cycastic.library_management.models.shelf;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookLocationDetails {
    private int id;
    private int bookId;
    private String shelfId;
}
