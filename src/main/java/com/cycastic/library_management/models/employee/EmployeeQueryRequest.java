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
public class EmployeeQueryRequest {
    private String id;
    private String fullName;
    private String hashedPassword;
    private String role;
    public EmployeeDetails toEmployee(){
        return new EmployeeDetails(id, fullName, hashedPassword, Role.toRole(role));
    }
}