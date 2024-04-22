package com.cycastic.library_management.services;

import com.cycastic.library_management.generated.tables.BookLocations;
import com.cycastic.library_management.generated.tables.Books;
import com.cycastic.library_management.generated.tables.BorrowedBooks;
import com.cycastic.library_management.generated.tables.Members;
import com.cycastic.library_management.models.IntegerWrapper;
import com.cycastic.library_management.models.borrows.BookReturnRequest;
import com.cycastic.library_management.models.borrows.BorrowDetails;
import com.cycastic.library_management.models.borrows.BorrowRequest;
import com.cycastic.library_management.models.borrows.FullBorrowDetails;
import com.cycastic.library_management.models.shelf.BookLocationCreationRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record8;
import org.jooq.SelectOnConditionStep;
import org.jooq.impl.DSL;
import org.jooq.types.ULong;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BorrowsService {
    @Getter
    @RequiredArgsConstructor
    public static class BookLocationNotExistException extends RuntimeException {
        private final BookLocationCreationRequest location;
    }
    @Getter
    @RequiredArgsConstructor
    public static class BorrowTicketNotExistException extends RuntimeException {
        private final int ticketId;
    }
    @Getter
    @RequiredArgsConstructor
    public static class AlreadyBorrowingException extends RuntimeException {
        private final String memberId;
        private final String memberName;
        private final int bookId;
        private final String bookName;
    }
    private final DSLContext dsl;
    private final BorrowedBooks borrowedBooks = BorrowedBooks.BORROWED_BOOKS;
    private final Books books = Books.BOOKS;
    private final Members members = Members.MEMBERS;
    private final BookLocations bookLocations = BookLocations.BOOK_LOCATIONS;

    public int borrowBook(@NonNull BorrowRequest request, String approvedBy){
        final var wrappedId = new IntegerWrapper();
        dsl.transaction(configuration -> {
            var currentTime = LocalDateTime.now();
            var tDsl = DSL.using(configuration);
            var alreadyBorrowing = getBorrowDetailsInternal(tDsl)
                    .where(borrowedBooks.MEMBER_ID.eq(request.getMemberId()).and(borrowedBooks.RETURNED.eq((byte)0)))
                    .fetchInto(FullBorrowDetails.class);
            if (!alreadyBorrowing.isEmpty()) {
                var rec = alreadyBorrowing.get(0);
                throw new AlreadyBorrowingException(rec.getMemberId(), rec.getMemberFullName(), rec.getBookId(), rec.getBookName());
            }
            var bookLoc = new BookLocationCreationRequest(request.getBookId(), request.getShelfId());
            var removed = ShelvesService.removeOneBookFromShelf(tDsl, bookLoc);
            if (removed == 0) throw new BookLocationNotExistException(bookLoc);
            var returned = tDsl.insertInto(borrowedBooks, borrowedBooks.MEMBER_ID, borrowedBooks.BOOK_ID, borrowedBooks.BORROWED_AT, borrowedBooks.APPROVED_BY, borrowedBooks.RETURNED)
                    .values(request.getMemberId(), ULong.valueOf(request.getBookId()), currentTime, approvedBy, (byte)0)
                    .returning(borrowedBooks.TICKET_ID)
                    .fetchOne(borrowedBooks.TICKET_ID);
            wrappedId.setValue(Objects.requireNonNullElse(returned, ULong.valueOf(0L)).intValue());
        });
        return wrappedId.getValue();
    }

    public @Nullable BorrowDetails getBorrowDetails(int ticketId) {
        var arr =  dsl.select()
                .from(borrowedBooks)
                .where(borrowedBooks.TICKET_ID.eq(ULong.valueOf(ticketId)))
                .fetchInto(BorrowDetails.class);
        return arr.isEmpty() ? null : arr.get(0);
    }

    private @NonNull SelectOnConditionStep<Record8<ULong, String, ULong, String, String, String, LocalDateTime, Byte>> getBorrowDetailsInternal(DSLContext customDsl){
        return customDsl.select(borrowedBooks.TICKET_ID.as("id"),
                        borrowedBooks.MEMBER_ID,
                        borrowedBooks.BOOK_ID,
                        members.FULL_NAME.as("member_full_name"),
                        books.TITLE.as("book_name"),
                        borrowedBooks.APPROVED_BY,
                        borrowedBooks.BORROWED_AT,
                        borrowedBooks.RETURNED)
                .from(borrowedBooks)
                .innerJoin(books).on(books.ID.eq(borrowedBooks.BOOK_ID))
                .innerJoin(members).on(members.ID.eq(borrowedBooks.MEMBER_ID));
    }

    private @NonNull SelectOnConditionStep<Record8<ULong, String, ULong, String, String, String, LocalDateTime, Byte>> getBorrowDetailsInternal(){
        return getBorrowDetailsInternal(dsl);
    }

    public Collection<FullBorrowDetails> getBorrowDetailsByBookId(int bookId){
        return getBorrowDetailsInternal()
                .where(borrowedBooks.BOOK_ID.eq(ULong.valueOf(bookId)))
                .fetchInto(FullBorrowDetails.class);
    }

    public Collection<FullBorrowDetails> getBorrowDetailsByBookId(int bookId, boolean returned){
        return getBorrowDetailsInternal()
                .where(borrowedBooks.BOOK_ID.eq(ULong.valueOf(bookId)).and(borrowedBooks.RETURNED.eq((byte)(returned ? 1 : 0))))
                .fetchInto(FullBorrowDetails.class);
    }

    public Collection<FullBorrowDetails> getBorrowDetailsByMemberId(String memberId){
        return getBorrowDetailsInternal()
                .where(borrowedBooks.MEMBER_ID.eq(memberId))
                .fetchInto(FullBorrowDetails.class);
    }

    public Collection<FullBorrowDetails> getBorrowDetailsByStatus(boolean returned){
        return getBorrowDetailsInternal()
                .where(borrowedBooks.RETURNED.eq((byte)(returned ? 1 : 0)))
                .fetchInto(FullBorrowDetails.class);
    }

    public Collection<FullBorrowDetails> getBorrowDetailsByMemberId(String memberId, boolean returned){
        return getBorrowDetailsInternal()
                .where(borrowedBooks.MEMBER_ID.eq(memberId).and(borrowedBooks.RETURNED.eq((byte)(returned ? 1 : 0))))
                .fetchInto(FullBorrowDetails.class);
    }

    public int returnBook(@NonNull BookReturnRequest ticket){
        final var wrappedInt = new IntegerWrapper(0);
        dsl.transaction(configuration -> {
            var tDsl = configuration.dsl();
            var batch = tDsl.batch(tDsl.insertInto(bookLocations, bookLocations.BOOK_ID, bookLocations.SHELF_ID)
                    .select(tDsl.select(borrowedBooks.BOOK_ID, DSL.inline(ticket.getShelfId()))
                            .from(borrowedBooks)
                            .where(borrowedBooks.TICKET_ID.eq(ULong.valueOf(ticket.getTicketId())))
                            .limit(1)),
                    tDsl.update(borrowedBooks)
                            .set(borrowedBooks.RETURNED, (byte)(1))
                            .where(borrowedBooks.TICKET_ID.eq(ULong.valueOf(ticket.getTicketId())))).execute();
            if (batch[0] == 0) throw new BorrowTicketNotExistException(ticket.getTicketId());
            wrappedInt.setValue(Arrays.stream(batch).sum());
        });
        return wrappedInt.getValue();
    }
}
