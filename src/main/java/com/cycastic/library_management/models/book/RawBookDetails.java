package com.cycastic.library_management.models.book;

import com.cycastic.library_management.models.author.AuthorDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RawBookDetails {
    private int id;
    private String title;
    private String capsuleUrl;
    private String isbn;
    private LocalDateTime publishedDate;
    private String name;
    private int inStorage;

    public BookDetails incorporateAuthorName(@NonNull ArrayList<String> authorNames){
        return BookDetails.builder()
                .id(id)
                .title(getTitle())
                .capsuleUrl(getCapsuleUrl())
                .authors(authorNames)
                .isbn(getIsbn())
                .publishedDate(getPublishedDate())
                .inStorage(getInStorage())
                .build();
    }
    public BookDetails incorporateAuthors(@NonNull Collection<AuthorDetails> authors){
        ArrayList<String> authorNames = new ArrayList<>();
        for (var author : authors){
            authorNames.add(author.getName());
        }
        return incorporateAuthorName(authorNames);
    }
}
