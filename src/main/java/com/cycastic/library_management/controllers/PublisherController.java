package com.cycastic.library_management.controllers;

import com.cycastic.library_management.models.publisher.PublisherUpdateRequest;
import com.cycastic.library_management.models.responses.AffectedResponse;
import com.cycastic.library_management.models.responses.IntIdResponse;
import com.cycastic.library_management.models.publisher.PublisherCreationRequest;
import com.cycastic.library_management.models.publisher.PublisherDetails;
import com.cycastic.library_management.services.EmployeesService;
import com.cycastic.library_management.services.PublishersService;
import lombok.RequiredArgsConstructor;
import org.jooq.exception.MappingException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/publishers")
@RequiredArgsConstructor
public class PublisherController {
    private final PublishersService publishersService;

    @PutMapping
    public IntIdResponse createPublisher(@RequestBody PublisherCreationRequest request){
        try {
            EmployeesService.superuserOnly();
            var ret = publishersService.createPublisher(request);
            if (ret == 0) throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create a publisher");
            return new IntIdResponse(ret);
        } catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Một hoặc nhiều trường bị thiếu");
        }
    }

    @GetMapping(produces = { "application/json" })
    public PublisherDetails getPublisher(@RequestParam("id") int id){
        try {
            var ret = publishersService.getPublisher(id);
            if (ret == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy");
            return ret;
        } catch (MappingException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cast failed");
        }
    }

    @PatchMapping
    public void editPublisher(@RequestBody PublisherUpdateRequest request){
        EmployeesService.superuserOnly();
        publishersService.updatePublisher(request);
    }

    @DeleteMapping
    public AffectedResponse deletePublisher(@RequestParam("id") int id){
        EmployeesService.superuserOnly();
        try {
            var ret = publishersService.deletePublisher(id);
            return new AffectedResponse(ret);
        } catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Một hoặc nhiều trường đang được tham chiếu từ bảng khác");
        }
    }
}
