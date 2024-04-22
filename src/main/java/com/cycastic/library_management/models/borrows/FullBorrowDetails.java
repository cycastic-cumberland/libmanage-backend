package com.cycastic.library_management.models.borrows;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullBorrowDetails {
    private int id;
    private String memberId;
    private int bookId;
    private String memberFullName;
    private String bookName;
    private String approvedBy;
    private LocalDateTime borrowedAt;
    private boolean returned;
}
