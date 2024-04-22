package com.cycastic.library_management.models.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeSelfUpdateRequest {
    private String fullName;
    private String password;
}
