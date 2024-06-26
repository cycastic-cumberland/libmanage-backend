/*
 * This file is generated by jOOQ.
 */
package com.cycastic.library_management.generated.tables;


import com.cycastic.library_management.generated.Indexes;
import com.cycastic.library_management.generated.Keys;
import com.cycastic.library_management.generated.LibraryManagement;
import com.cycastic.library_management.generated.tables.records.ShelvesRecord;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function2;
import org.jooq.Index;
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


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Shelves extends TableImpl<ShelvesRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>library_management.shelves</code>
     */
    public static final Shelves SHELVES = new Shelves();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ShelvesRecord> getRecordType() {
        return ShelvesRecord.class;
    }

    /**
     * The column <code>library_management.shelves.id</code>.
     */
    public final TableField<ShelvesRecord, String> ID = createField(DSL.name("id"), SQLDataType.VARCHAR(8).nullable(false), this, "");

    /**
     * The column <code>library_management.shelves.shelf_name</code>.
     */
    public final TableField<ShelvesRecord, String> SHELF_NAME = createField(DSL.name("shelf_name"), SQLDataType.VARCHAR(32).nullable(false), this, "");

    private Shelves(Name alias, Table<ShelvesRecord> aliased) {
        this(alias, aliased, null);
    }

    private Shelves(Name alias, Table<ShelvesRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>library_management.shelves</code> table reference
     */
    public Shelves(String alias) {
        this(DSL.name(alias), SHELVES);
    }

    /**
     * Create an aliased <code>library_management.shelves</code> table reference
     */
    public Shelves(Name alias) {
        this(alias, SHELVES);
    }

    /**
     * Create a <code>library_management.shelves</code> table reference
     */
    public Shelves() {
        this(DSL.name("shelves"), null);
    }

    public <O extends Record> Shelves(Table<O> child, ForeignKey<O, ShelvesRecord> key) {
        super(child, key, SHELVES);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : LibraryManagement.LIBRARY_MANAGEMENT;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.SHELVES_SHELVES_SHELF_NAME_INDEX);
    }

    @Override
    public UniqueKey<ShelvesRecord> getPrimaryKey() {
        return Keys.KEY_SHELVES_PRIMARY;
    }

    @Override
    public Shelves as(String alias) {
        return new Shelves(DSL.name(alias), this);
    }

    @Override
    public Shelves as(Name alias) {
        return new Shelves(alias, this);
    }

    @Override
    public Shelves as(Table<?> alias) {
        return new Shelves(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Shelves rename(String name) {
        return new Shelves(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Shelves rename(Name name) {
        return new Shelves(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Shelves rename(Table<?> name) {
        return new Shelves(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<String, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function2<? super String, ? super String, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function2<? super String, ? super String, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
