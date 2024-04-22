package com.cycastic.library_management.services;

import com.cycastic.library_management.generated.tables.BorrowedBooks;
import com.cycastic.library_management.generated.tables.Employees;
import com.cycastic.library_management.generated.tables.records.EmployeesRecord;
import com.cycastic.library_management.models.Role;
import com.cycastic.library_management.models.employee.*;
import org.jooq.DSLContext;
import org.jooq.UpdateSetStep;
import org.jooq.UpdateWhereStep;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collection;

@Service
public class EmployeesService {
    @Autowired
    private DSLContext dsl;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Employees employees = Employees.EMPLOYEES;
    private final BorrowedBooks borrowedBooks = BorrowedBooks.BORROWED_BOOKS;

    public @NonNull String createEmployee(@NonNull EmployeeCreationRequest request) {
        var hashedPassword = bCryptPasswordEncoder.encode(request.getPassword());
        dsl.insertInto(employees, employees.ID, employees.FULL_NAME, employees.HASHED_PASSWORD, employees.ROLE)
                .values(request.getId(), request.getFullName(), hashedPassword, request.getRole())
                .execute();
        return request.getId();
    }

    public @Nullable EmployeeDetails getEmployeeById(@NonNull String id){
        var record = dsl.select()
                .from(employees)
                .where(employees.ID.eq(id))
                .fetchOne();
        return record == null ? null : record.into(EmployeeQueryRequest.class).toEmployee();
    }

    public int countEmployee() {
        return dsl.fetchCount(employees);
    }

    public Collection<EmployeeQueryResponse> getAllEmployees(){
        return dsl.select().from(employees).fetchInto(EmployeeQueryResponse.class);
    }

    public Collection<EmployeeQueryResponse> searchEmployees(String pattern){
        return dsl.select()
                .from(employees)
                .where(DSL.upper(employees.FULL_NAME).like(String.format("%%%s%%", pattern).toUpperCase()).or(employees.ID.eq(pattern)))
                .fetchInto(EmployeeQueryResponse.class);
    }

    public void updateEmployee(@NonNull EmployeeUpdateRequest request){
        UpdateSetStep<EmployeesRecord> builder = dsl.update(employees);
        if (request.getFullName() != null){
            builder = builder.set(employees.FULL_NAME, request.getFullName());
        }
        if (request.getPassword() != null){
            var hashedPassword = bCryptPasswordEncoder.encode(request.getPassword());
            builder = builder.set(employees.HASHED_PASSWORD, hashedPassword);
        }
        if (request.getRole() != null){
            builder = builder.set(employees.ROLE, request.getRole());
        }
        var classDetail = UpdateWhereStep.class;
        classDetail.cast(builder).where(employees.ID.eq(request.getId())).execute();
    }

    public int deleteEmployee(String id){
        return Arrays.stream(dsl.batch(dsl.deleteFrom(borrowedBooks).where(borrowedBooks.APPROVED_BY.eq(id).and(borrowedBooks.RETURNED.eq((byte)1))),
                dsl.deleteFrom(employees).where(employees.ID.eq(id)))
                        .execute()).sum();
    }

    public static EmployeeDetails getSecurityContext(){
        var user = SecurityContextHolder.getContext().getAuthentication();
        return EmployeeDetails.fromAuthentication(user);
    }

    public static EmployeeDetails superuserOnly(){
        var self = EmployeesService.getSecurityContext();
        if (self.getRole() != Role.SUPERUSER) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not permitted");
        return self;
    }
}
