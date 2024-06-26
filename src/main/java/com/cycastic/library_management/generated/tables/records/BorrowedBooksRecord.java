/*
 * This file is generated by jOOQ.
 */
package com.cycastic.library_management.generated.tables.records;


import com.cycastic.library_management.generated.tables.BorrowedBooks;

import java.time.LocalDateTime;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.ULong;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class BorrowedBooksRecord extends UpdatableRecordImpl<BorrowedBooksRecord> implements Record6<ULong, String, ULong, LocalDateTime, String, Byte> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>library_management.borrowed_books.ticket_id</code>.
     */
    public void setTicketId(ULong value) {
        set(0, value);
    }

    /**
     * Getter for <code>library_management.borrowed_books.ticket_id</code>.
     */
    public ULong getTicketId() {
        return (ULong) get(0);
    }

    /**
     * Setter for <code>library_management.borrowed_books.member_id</code>.
     */
    public void setMemberId(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>library_management.borrowed_books.member_id</code>.
     */
    public String getMemberId() {
        return (String) get(1);
    }

    /**
     * Setter for <code>library_management.borrowed_books.book_id</code>.
     */
    public void setBookId(ULong value) {
        set(2, value);
    }

    /**
     * Getter for <code>library_management.borrowed_books.book_id</code>.
     */
    public ULong getBookId() {
        return (ULong) get(2);
    }

    /**
     * Setter for <code>library_management.borrowed_books.borrowed_at</code>.
     */
    public void setBorrowedAt(LocalDateTime value) {
        set(3, value);
    }

    /**
     * Getter for <code>library_management.borrowed_books.borrowed_at</code>.
     */
    public LocalDateTime getBorrowedAt() {
        return (LocalDateTime) get(3);
    }

    /**
     * Setter for <code>library_management.borrowed_books.approved_by</code>.
     */
    public void setApprovedBy(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>library_management.borrowed_books.approved_by</code>.
     */
    public String getApprovedBy() {
        return (String) get(4);
    }

    /**
     * Setter for <code>library_management.borrowed_books.returned</code>.
     */
    public void setReturned(Byte value) {
        set(5, value);
    }

    /**
     * Getter for <code>library_management.borrowed_books.returned</code>.
     */
    public Byte getReturned() {
        return (Byte) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<ULong> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row6<ULong, String, ULong, LocalDateTime, String, Byte> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    @Override
    public Row6<ULong, String, ULong, LocalDateTime, String, Byte> valuesRow() {
        return (Row6) super.valuesRow();
    }

    @Override
    public Field<ULong> field1() {
        return BorrowedBooks.BORROWED_BOOKS.TICKET_ID;
    }

    @Override
    public Field<String> field2() {
        return BorrowedBooks.BORROWED_BOOKS.MEMBER_ID;
    }

    @Override
    public Field<ULong> field3() {
        return BorrowedBooks.BORROWED_BOOKS.BOOK_ID;
    }

    @Override
    public Field<LocalDateTime> field4() {
        return BorrowedBooks.BORROWED_BOOKS.BORROWED_AT;
    }

    @Override
    public Field<String> field5() {
        return BorrowedBooks.BORROWED_BOOKS.APPROVED_BY;
    }

    @Override
    public Field<Byte> field6() {
        return BorrowedBooks.BORROWED_BOOKS.RETURNED;
    }

    @Override
    public ULong component1() {
        return getTicketId();
    }

    @Override
    public String component2() {
        return getMemberId();
    }

    @Override
    public ULong component3() {
        return getBookId();
    }

    @Override
    public LocalDateTime component4() {
        return getBorrowedAt();
    }

    @Override
    public String component5() {
        return getApprovedBy();
    }

    @Override
    public Byte component6() {
        return getReturned();
    }

    @Override
    public ULong value1() {
        return getTicketId();
    }

    @Override
    public String value2() {
        return getMemberId();
    }

    @Override
    public ULong value3() {
        return getBookId();
    }

    @Override
    public LocalDateTime value4() {
        return getBorrowedAt();
    }

    @Override
    public String value5() {
        return getApprovedBy();
    }

    @Override
    public Byte value6() {
        return getReturned();
    }

    @Override
    public BorrowedBooksRecord value1(ULong value) {
        setTicketId(value);
        return this;
    }

    @Override
    public BorrowedBooksRecord value2(String value) {
        setMemberId(value);
        return this;
    }

    @Override
    public BorrowedBooksRecord value3(ULong value) {
        setBookId(value);
        return this;
    }

    @Override
    public BorrowedBooksRecord value4(LocalDateTime value) {
        setBorrowedAt(value);
        return this;
    }

    @Override
    public BorrowedBooksRecord value5(String value) {
        setApprovedBy(value);
        return this;
    }

    @Override
    public BorrowedBooksRecord value6(Byte value) {
        setReturned(value);
        return this;
    }

    @Override
    public BorrowedBooksRecord values(ULong value1, String value2, ULong value3, LocalDateTime value4, String value5, Byte value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached BorrowedBooksRecord
     */
    public BorrowedBooksRecord() {
        super(BorrowedBooks.BORROWED_BOOKS);
    }

    /**
     * Create a detached, initialised BorrowedBooksRecord
     */
    public BorrowedBooksRecord(ULong ticketId, String memberId, ULong bookId, LocalDateTime borrowedAt, String approvedBy, Byte returned) {
        super(BorrowedBooks.BORROWED_BOOKS);

        setTicketId(ticketId);
        setMemberId(memberId);
        setBookId(bookId);
        setBorrowedAt(borrowedAt);
        setApprovedBy(approvedBy);
        setReturned(returned);
    }

    /**
     * Create a detached, initialised BorrowedBooksRecord
     */
    public BorrowedBooksRecord(com.cycastic.library_management.generated.tables.pojos.BorrowedBooks value) {
        super(BorrowedBooks.BORROWED_BOOKS);

        if (value != null) {
            setTicketId(value.getTicketId());
            setMemberId(value.getMemberId());
            setBookId(value.getBookId());
            setBorrowedAt(value.getBorrowedAt());
            setApprovedBy(value.getApprovedBy());
            setReturned(value.getReturned());
        }
    }
}
