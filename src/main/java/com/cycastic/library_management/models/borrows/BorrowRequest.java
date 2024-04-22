package com.cycastic.library_management.models.borrows;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowRequest {
    private String memberId;
    private int bookId;
    private String shelfId;
}
