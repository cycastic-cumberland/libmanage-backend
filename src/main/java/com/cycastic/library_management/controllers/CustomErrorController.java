package com.cycastic.library_management.controllers;

import org.jooq.tools.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Objects;

@ControllerAdvice
public class CustomErrorController extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> ResponseStatusExceptionHandler(@NonNull ResponseStatusException e,
                                                                 @NonNull HttpServletRequest req) {
//        logger.debug("Exception resolved", e);
        var currentDateTime = OffsetDateTime.now(ZoneId.of("UTC"));
        var response = new JSONObject();
        response.put("timestamp", currentDateTime.toString());
        response.put("status", e.getStatus().value());
        response.put("error", e.getStatus().getReasonPhrase());
        response.put("message", Objects.requireNonNullElse(e.getReason(), "Unknown error caught"));
        response.put("path", req.getRequestURI());
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(response.toString(), headers, e.getStatus());
    }
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> GeneralExceptionHandler(Exception e) {
//        logger.warn("Exception resolved", e);
//        var response = new JSONObject();
//        response.put("message","Internal server error");
//        var headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        return new ResponseEntity<>(response.toString(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
