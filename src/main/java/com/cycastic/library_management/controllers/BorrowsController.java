package com.cycastic.library_management.controllers;

import com.cycastic.library_management.models.borrows.BookReturnRequest;
import com.cycastic.library_management.models.borrows.BorrowDetails;
import com.cycastic.library_management.models.borrows.BorrowDetailsList;
import com.cycastic.library_management.models.borrows.BorrowRequest;
import com.cycastic.library_management.models.responses.AffectedResponse;
import com.cycastic.library_management.models.responses.IntIdResponse;
import com.cycastic.library_management.services.BorrowsService;
import com.cycastic.library_management.services.EmployeesService;
import lombok.RequiredArgsConstructor;
import org.jooq.exception.MappingException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/borrows")
@RequiredArgsConstructor
public class BorrowsController {
    private final BorrowsService borrowsService;

    @PutMapping
    public IntIdResponse borrowBook(@RequestBody BorrowRequest request){
        try {
            var self = EmployeesService.getSecurityContext();
            return new IntIdResponse(borrowsService.borrowBook(request, self.getId()));
        } catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID thành viên hoặc kệ không tồn tại");
        } catch (BorrowsService.BookLocationNotExistException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Vị trí sách không tồn tại: (bookId=%d, shelfId=%s)",
                    e.getLocation().getBookId(), e.getLocation().getShelfId()));
        } catch (BorrowsService.AlreadyBorrowingException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Thành viên \"%s\" (ID: \"%s\") đang mượn \"%s\"",
                    e.getMemberName(), e.getMemberId(), e.getBookName()));
        }
    }

    @GetMapping(produces = { "application/json" })
    public BorrowDetails getBorrowDetails(@RequestParam("id") int id){
        try {
            var ret = borrowsService.getBorrowDetails(id);
            if (ret == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy");
            return ret;
        } catch (MappingException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cast failed");
        }
    }

    @GetMapping(value = { "by-book-id" }, produces = { "application/json" })
    public BorrowDetailsList getBorrowDetailsByBookId(@RequestParam("id") int id, @RequestParam(value = "returned", required = false) Boolean returned){
        try {
            return new BorrowDetailsList(returned != null ?
                    borrowsService.getBorrowDetailsByBookId(id, returned) :
                    borrowsService.getBorrowDetailsByBookId(id));
        } catch (MappingException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cast failed");
        }
    }

    @GetMapping(value = {"by-member-id"}, produces = { "application/json" })
    public BorrowDetailsList getBorrowDetailsByMemberId(@RequestParam("id") String id, @RequestParam(value = "returned", required = false) Boolean returned){
        try {
            return new BorrowDetailsList(returned != null ?
                    borrowsService.getBorrowDetailsByMemberId(id, returned) :
                    borrowsService.getBorrowDetailsByMemberId(id));
        } catch (MappingException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cast failed");
        }
    }

    @GetMapping(value = {"by-status"}, produces = { "application/json" })
    public BorrowDetailsList getBorrowDetailsByStatus(@RequestParam("returned") boolean returned){
        try {
            return new BorrowDetailsList(borrowsService.getBorrowDetailsByStatus(returned));
        } catch (MappingException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cast failed");
        }
    }

    @PostMapping(value = {"return-one"}, produces = { "application/json" })
    public AffectedResponse returnBooks(@RequestBody BookReturnRequest request){
        return new AffectedResponse(borrowsService.returnBook(request));
    }
}
