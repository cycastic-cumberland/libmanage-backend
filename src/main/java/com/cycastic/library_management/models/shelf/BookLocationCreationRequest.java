package com.cycastic.library_management.models.shelf;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookLocationCreationRequest {
    private int bookId;
    private String shelfId;
}
