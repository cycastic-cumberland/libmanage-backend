/*
 * This file is generated by jOOQ.
 */
package com.cycastic.library_management.generated;


import com.cycastic.library_management.generated.tables.Authors;
import com.cycastic.library_management.generated.tables.Books;
import com.cycastic.library_management.generated.tables.Employees;
import com.cycastic.library_management.generated.tables.Members;
import com.cycastic.library_management.generated.tables.Shelves;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling indexes of tables in library_management.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index AUTHORS_AUTHORS_NAME_INDEX = Internal.createIndex(DSL.name("authors_name_index"), Authors.AUTHORS, new OrderField[] { Authors.AUTHORS.NAME }, false);
    public static final Index BOOKS_BOOKS_TITLE_INDEX = Internal.createIndex(DSL.name("books_title_index"), Books.BOOKS, new OrderField[] { Books.BOOKS.TITLE }, false);
    public static final Index EMPLOYEES_EMPLOYEES_FULL_NAME_INDEX = Internal.createIndex(DSL.name("employees_full_name_index"), Employees.EMPLOYEES, new OrderField[] { Employees.EMPLOYEES.FULL_NAME }, false);
    public static final Index MEMBERS_MEMBERS_FULL_NAME_INDEX = Internal.createIndex(DSL.name("members_full_name_index"), Members.MEMBERS, new OrderField[] { Members.MEMBERS.FULL_NAME }, false);
    public static final Index SHELVES_SHELVES_SHELF_NAME_INDEX = Internal.createIndex(DSL.name("shelves_shelf_name_index"), Shelves.SHELVES, new OrderField[] { Shelves.SHELVES.SHELF_NAME }, false);
}
