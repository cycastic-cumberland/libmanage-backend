package com.cycastic.library_management.models.publisher;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublisherCreationRequest {
    private String email;
    private String publisherName;
}
