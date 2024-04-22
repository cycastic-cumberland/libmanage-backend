package com.cycastic.library_management.controllers;

import com.cycastic.library_management.models.Role;
import com.cycastic.library_management.models.responses.TokenResponse;
import com.cycastic.library_management.models.employee.EmployeeLoginRequest;
import com.cycastic.library_management.services.EmployeesService;
import com.cycastic.library_management.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final JwtService jwtService;
    private final EmployeesService employeesService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping
    public TokenResponse login(@RequestBody EmployeeLoginRequest request){
        var employee = employeesService.getEmployeeById(request.getId());
        String STANDARD_RESPONSE = "Username or password does not match";
        if (employee == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, STANDARD_RESPONSE);
        var matched = bCryptPasswordEncoder.matches(request.getPassword(), employee.getHashedPassword());
        if (!matched) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, STANDARD_RESPONSE);
        var token = jwtService.generateToken(employee);
        return new TokenResponse(token, employee.getUsername(), employee.getFullName(), Role.toString(employee.getRole()));
    }
}
