package com.cycastic.library_management.controllers;

import com.cycastic.library_management.models.CountReturn;
import com.cycastic.library_management.models.book.*;
import com.cycastic.library_management.models.responses.AffectedResponse;
import com.cycastic.library_management.models.responses.IntIdResponse;
import com.cycastic.library_management.models.shelf.ShelvesReturn;
import com.cycastic.library_management.services.BooksService;
import com.cycastic.library_management.services.BorrowsService;
import lombok.RequiredArgsConstructor;
import org.jooq.exception.MappingException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BooksController {
    private final BooksService booksService;

    @PutMapping
    public IntIdResponse createBook(@RequestBody BookCreationRequest request){
        try {
            var ret = booksService.createBook(request);
            return new IntIdResponse(ret);
        } catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Một hoặc nhiều trường bị thiếu");
        }
    }

    @PutMapping(value = {"shelf"}, produces = { "application/json" })
    public AffectedResponse addToShelf(@RequestParam int bookId, @RequestParam String shelfId){
        try {
            return new AffectedResponse(booksService.addToShelf(bookId, shelfId));
        } catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID sách hoặc kệ không tồn tại");
        } catch (BorrowsService.BookLocationNotExistException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Vị trí sách không tồn tại: (bookId=%d, shelfId=%s)",
                    e.getLocation().getBookId(), e.getLocation().getShelfId()));
        }
    }

    @GetMapping(produces = { "application/json" })
    public BookDetails getBook(@RequestParam("id") int id){
        try {
            var ret = booksService.getBook(id);
            if (ret == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy");
            return ret;
        } catch (MappingException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cast failed");
        }
    }

    @GetMapping(value = { "by-isbn" }, produces = { "application/json" })
    public AssociatedBooksResponse getBookByIsbn(@RequestParam("isbn") String isbn){
        try {
            return new AssociatedBooksResponse(booksService.getBooksByIsbn(isbn));
        } catch (MappingException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cast failed");
        }
    }

    @GetMapping(value = { "associated/authors" }, produces = { "application/json" })
    public AssociatedAuthorsResponse getAssociatedAuthors(@RequestParam("book_id") int bookId){
        try {
            var ret = booksService.getAssociatedAuthors(bookId);
            return new AssociatedAuthorsResponse(ret);
        } catch (MappingException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cast failed");
        }
    }

    @GetMapping(value = { "all" }, produces = { "application/json" })
    public AssociatedBooksResponse getAllBooks(){
        try {
            var ret = booksService.getAllBooks();
            return new AssociatedBooksResponse(ret);
        } catch (MappingException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cast failed");
        }
    }

    @GetMapping(value = { "search" }, produces = { "application/json" })
    public AssociatedBooksResponse searchBooks(@RequestParam String pattern){
        try {
            var ret = booksService.searchBooks(pattern);
            return new AssociatedBooksResponse(ret);
        } catch (MappingException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cast failed");
        }
    }

    @GetMapping(value = { "associated/books" }, produces = { "application/json" })
    public AssociatedBooksResponse getAssociatedBooks(@RequestParam("author_name") String authorName){
        try {
            var ret = booksService.getAssociatedBooks(authorName);
            if (ret == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy");
            return new AssociatedBooksResponse(ret);
        } catch (MappingException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cast failed");
        }
    }

    @GetMapping(value = { "count/all" }, produces = { "application/json" })
    public CountReturn countBooks(){
        return new CountReturn(booksService.countBooks());
    }

    @GetMapping(value = { "count/copy" }, produces = { "application/json" })
    public CountReturn countBookCopies(@RequestParam("id") int bookId){
        return new CountReturn(booksService.countCopy(bookId));
    }

    @GetMapping(value = { "locations" }, produces = { "application/json" })
    public ShelvesReturn getContainingShelves(@RequestParam("id") int bookId){
        return new ShelvesReturn(booksService.getContainingShelves(bookId));
    }

    @PatchMapping
    public void editBook(@RequestBody BookUpdateRequest request){
        booksService.updateBook(request);
    }

    @DeleteMapping
    public AffectedResponse deleteBook(@RequestParam("id") int id){
        try {
            var ret = booksService.deleteBook(id);
            return new AffectedResponse(ret);
        } catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Một hoặc nhiều trường đang được tham chiếu từ bảng khác");
        }
    }
}
