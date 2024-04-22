package com.cycastic.library_management.services;

import com.cycastic.library_management.models.employee.EmployeeDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@PropertySource("classpath:application.properties")
@RequiredArgsConstructor
public class JwtService {
    private final EmployeesService employeesService;
    private final Logger logger = LoggerFactory.getLogger(JwtService.class);
    @Autowired
    private Environment environment;
    public @Nullable EmployeeDetails extractUser(@NonNull String jwt){
        try {
            if (isTokenExpired(jwt)) return null;
            var extracted = extractClaim(jwt, Claims::getSubject);
            return employeesService.getEmployeeById(extracted);
        } catch (Exception e){
            logger.debug("Failed to extract claims from given token", e);
            return null;
        }
    }

    public @NonNull String generateToken(@NonNull UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    public @NonNull String generateToken(@NonNull Map<String, Object> extraClaims, @NonNull UserDetails userDetails){
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + getValidTime()))
                .signWith(getJwtSecret(), SignatureAlgorithm.HS256)
                .compact();
    }

    private <T> @NonNull T extractClaim(@NonNull String token, @NonNull Function<Claims, T> claimsResolver){
        final var claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private boolean isTokenExpired(@NonNull String token){
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    private @NonNull Key getJwtSecret(){
        var key = environment.getRequiredProperty("app.jwt.secret");
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private long getValidTime(){
        return Long.parseLong(environment.getRequiredProperty("app.jwt.valid-for"));
    }

    private Claims extractAllClaims(@NonNull String jwt){
        return Jwts.parserBuilder()
                .setSigningKey(getJwtSecret())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }
}
