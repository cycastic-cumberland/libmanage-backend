package com.cycastic.library_management.models.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeQueryResponseList {
    private Collection<EmployeeQueryResponse> employees;
}
