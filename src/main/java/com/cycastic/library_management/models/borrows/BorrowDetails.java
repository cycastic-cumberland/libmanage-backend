package com.cycastic.library_management.models.borrows;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowDetails {
    private int id;
    private String memberId;
    private int bookId;
    private String approvedBy;
    private OffsetDateTime borrowedAt;
    private boolean returned;
}
