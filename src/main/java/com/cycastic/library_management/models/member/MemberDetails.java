package com.cycastic.library_management.models.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDetails {
    private String id;
    private String fullName;
    private LocalDateTime joinDate;
}
