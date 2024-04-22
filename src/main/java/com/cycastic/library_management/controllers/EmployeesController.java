package com.cycastic.library_management.controllers;

import com.cycastic.library_management.models.CountReturn;
import com.cycastic.library_management.models.employee.*;
import com.cycastic.library_management.models.responses.AffectedResponse;
import com.cycastic.library_management.models.responses.StringIdResponse;
import com.cycastic.library_management.services.EmployeesService;
import lombok.RequiredArgsConstructor;
import org.jooq.exception.MappingException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeesController {
    private final EmployeesService employeesService;

    // POST for posterity's sake...
    @PostMapping
    public StringIdResponse createEmployee(@RequestBody EmployeeCreationRequest request){
        try {
            EmployeesService.superuserOnly();
            return new StringIdResponse(employeesService.createEmployee(request));
        } catch (DuplicateKeyException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Tên đăng nhập bị trùng");
        } catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Một hoặc nhiều trường bị thiếu");
        }
    }

    @GetMapping(produces = { "application/json" })
    public EmployeeQueryResponse getEmployee(@RequestParam("id") String id){
        try {
            EmployeesService.superuserOnly();
            var ret = employeesService.getEmployeeById(id);
            if (ret == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy");
            return EmployeeQueryResponse.fromEmployee(ret);
        } catch (MappingException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cast failed");
        }
    }

    @PatchMapping
    public void editSelf(@RequestBody EmployeeSelfUpdateRequest request){
        var self = EmployeesService.getSecurityContext();
        employeesService.updateEmployee(EmployeeUpdateRequest.builder()
                        .id(self.getId())
                        .fullName(request.getFullName())
                        .password(request.getPassword())
                .build());
    }

    @GetMapping(value = { "count/all" }, produces = { "application/json" })
    public CountReturn countEmployee(){
        return new CountReturn(employeesService.countEmployee());
    }

    @GetMapping(value = { "all" }, produces = { "application/json" })
    public EmployeeQueryResponseList getAllEmployees(){
        return new EmployeeQueryResponseList(employeesService.getAllEmployees());
    }

    @GetMapping(value = { "search" }, produces = { "application/json" })
    public EmployeeQueryResponseList searchEmployees(@RequestParam("pattern") String pattern){
        return new EmployeeQueryResponseList(employeesService.searchEmployees(pattern));
    }

    @PatchMapping("/other")
    public void editEmployee(@RequestBody EmployeeUpdateRequest request){
        EmployeesService.superuserOnly();
        employeesService.updateEmployee(request);
    }

    @DeleteMapping
    public AffectedResponse deleteEmployee(@RequestParam("id") String id){
        var self = EmployeesService.superuserOnly();
        if (self.getId().equals(id)) throw new ResponseStatusException(HttpStatus.CONFLICT, "Không thể tự xóa bản thân");
        try {
            var affected = employeesService.deleteEmployee(id);
            return new AffectedResponse(affected);
        } catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Một hoặc nhiều trường đang được tham chiếu từ bảng khác");
        }
    }

    @GetMapping("/self")
    public EmployeeQueryResponse getSelf(){
        return EmployeeQueryResponse.fromEmployee(EmployeesService.getSecurityContext());
    }
}
