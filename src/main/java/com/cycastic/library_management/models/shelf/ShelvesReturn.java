package com.cycastic.library_management.models.shelf;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShelvesReturn {
    private Collection<ShelfDetails> shelves;
}
