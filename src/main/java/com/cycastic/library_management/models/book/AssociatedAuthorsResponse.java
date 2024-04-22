package com.cycastic.library_management.models.book;

import com.cycastic.library_management.models.author.AuthorDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssociatedAuthorsResponse {
    private Collection<AuthorDetails> authors;
}
