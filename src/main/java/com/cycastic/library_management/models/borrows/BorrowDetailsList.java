package com.cycastic.library_management.models.borrows;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowDetailsList {
    private Collection<FullBorrowDetails> details;
}
