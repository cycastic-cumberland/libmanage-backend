package com.cycastic.library_management.models.publisher;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublisherUpdateRequest {
    private int id;
    private String email;
    private String publisherName;
}
