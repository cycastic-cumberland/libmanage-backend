package com.cycastic.library_management.controllers;

import com.cycastic.library_management.models.responses.AffectedResponse;
import com.cycastic.library_management.models.responses.StringIdResponse;
import com.cycastic.library_management.models.shelf.ShelfDetails;
import com.cycastic.library_management.models.shelf.ShelvesInfo;
import com.cycastic.library_management.models.shelf.ShelvesReturn;
import com.cycastic.library_management.services.ShelvesService;
import lombok.RequiredArgsConstructor;
import org.jooq.exception.MappingException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/shelves")
@RequiredArgsConstructor
public class ShelvesController {
    private final ShelvesService shelvesService;

    @PutMapping
    public StringIdResponse createShelf(@RequestBody ShelfDetails request){
        try {
            var ret = shelvesService.createShelf(request);
            return new StringIdResponse(ret);
        } catch (DuplicateKeyException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Mã kệ bị trùng");
        } catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Một hoặc nhiều trường bị thiếu");
        }
    }

    @GetMapping(produces = { "application/json" })
    public ShelfDetails getShelf(@RequestParam("id") String id){
        try {
            var ret = shelvesService.getShelf(id);
            if (ret == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy");
            return ret;
        } catch (MappingException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cast failed");
        }
    }

    @GetMapping(value = {"all"}, produces = { "application/json" })
    public ShelvesReturn getAllShelves(){
        try {
            return new ShelvesReturn(shelvesService.getAllShelves());
        } catch (MappingException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cast failed");
        }
    }

    @GetMapping(value = {"all/info"}, produces = { "application/json" })
    public ShelvesInfo getShelvesInfo(){
        try {
            return new ShelvesInfo(shelvesService.getShelvesInfo());
        } catch (MappingException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cast failed");
        }
    }

    @GetMapping(value = {"all/search"}, produces = { "application/json" })
    public ShelvesInfo searchShelvesInfo(@RequestParam("pattern") String pattern){
        try {
            return new ShelvesInfo(shelvesService.searchShelvesInfo(pattern));
        } catch (MappingException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cast failed");
        }
    }

    @PatchMapping
    public void editShelf(@RequestBody ShelfDetails request){
        shelvesService.updateShelf(request);
    }

    @DeleteMapping
    public AffectedResponse deleteShelf(@RequestParam("id") String id){
        try {
            var ret = shelvesService.deleteShelf(id);
            return new AffectedResponse(ret);
        } catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Một hoặc nhiều trường đang được tham chiếu từ bảng khác");
        }
    }
}
