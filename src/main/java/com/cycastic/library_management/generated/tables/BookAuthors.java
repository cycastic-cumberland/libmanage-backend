/*
 * This file is generated by jOOQ.
 */
package com.cycastic.library_management.generated.tables;


import com.cycastic.library_management.generated.Keys;
import com.cycastic.library_management.generated.LibraryManagement;
import com.cycastic.library_management.generated.tables.records.BookAuthorsRecord;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function2;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row2;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.types.ULong;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class BookAuthors extends TableImpl<BookAuthorsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>library_management.book_authors</code>
     */
    public static final BookAuthors BOOK_AUTHORS = new BookAuthors();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<BookAuthorsRecord> getRecordType() {
        return BookAuthorsRecord.class;
    }

    /**
     * The column <code>library_management.book_authors.book_id</code>.
     */
    public final TableField<BookAuthorsRecord, ULong> BOOK_ID = createField(DSL.name("book_id"), SQLDataType.BIGINTUNSIGNED.nullable(false), this, "");

    /**
     * The column <code>library_management.book_authors.author_id</code>.
     */
    public final TableField<BookAuthorsRecord, ULong> AUTHOR_ID = createField(DSL.name("author_id"), SQLDataType.BIGINTUNSIGNED.nullable(false), this, "");

    private BookAuthors(Name alias, Table<BookAuthorsRecord> aliased) {
        this(alias, aliased, null);
    }

    private BookAuthors(Name alias, Table<BookAuthorsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>library_management.book_authors</code> table
     * reference
     */
    public BookAuthors(String alias) {
        this(DSL.name(alias), BOOK_AUTHORS);
    }

    /**
     * Create an aliased <code>library_management.book_authors</code> table
     * reference
     */
    public BookAuthors(Name alias) {
        this(alias, BOOK_AUTHORS);
    }

    /**
     * Create a <code>library_management.book_authors</code> table reference
     */
    public BookAuthors() {
        this(DSL.name("book_authors"), null);
    }

    public <O extends Record> BookAuthors(Table<O> child, ForeignKey<O, BookAuthorsRecord> key) {
        super(child, key, BOOK_AUTHORS);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : LibraryManagement.LIBRARY_MANAGEMENT;
    }

    @Override
    public UniqueKey<BookAuthorsRecord> getPrimaryKey() {
        return Keys.KEY_BOOK_AUTHORS_PRIMARY;
    }

    @Override
    public List<ForeignKey<BookAuthorsRecord, ?>> getReferences() {
        return Arrays.asList(Keys.BOOK_AUTHORS_BOOKS_ID_FK, Keys.BOOK_AUTHORS_AUTHORS_ID_FK);
    }

    private transient Books _books;
    private transient Authors _authors;

    /**
     * Get the implicit join path to the <code>library_management.books</code>
     * table.
     */
    public Books books() {
        if (_books == null)
            _books = new Books(this, Keys.BOOK_AUTHORS_BOOKS_ID_FK);

        return _books;
    }

    /**
     * Get the implicit join path to the <code>library_management.authors</code>
     * table.
     */
    public Authors authors() {
        if (_authors == null)
            _authors = new Authors(this, Keys.BOOK_AUTHORS_AUTHORS_ID_FK);

        return _authors;
    }

    @Override
    public BookAuthors as(String alias) {
        return new BookAuthors(DSL.name(alias), this);
    }

    @Override
    public BookAuthors as(Name alias) {
        return new BookAuthors(alias, this);
    }

    @Override
    public BookAuthors as(Table<?> alias) {
        return new BookAuthors(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public BookAuthors rename(String name) {
        return new BookAuthors(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public BookAuthors rename(Name name) {
        return new BookAuthors(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public BookAuthors rename(Table<?> name) {
        return new BookAuthors(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<ULong, ULong> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function2<? super ULong, ? super ULong, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function2<? super ULong, ? super ULong, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
