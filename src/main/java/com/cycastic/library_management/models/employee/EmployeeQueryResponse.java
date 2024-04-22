package com.cycastic.library_management.models.employee;

import com.cycastic.library_management.models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeQueryResponse {
    private String id;
    private String fullName;
    private String role;

    public static EmployeeQueryResponse fromEmployee(EmployeeDetails details){
        return new EmployeeQueryResponse(details.getId(), details.getFullName(), Role.toString(details.getRole()));
    }
}
