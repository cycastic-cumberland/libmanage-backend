/*
 * This file is generated by jOOQ.
 */
package com.cycastic.library_management.generated.tables;


import com.cycastic.library_management.generated.Indexes;
import com.cycastic.library_management.generated.Keys;
import com.cycastic.library_management.generated.LibraryManagement;
import com.cycastic.library_management.generated.tables.records.MembersRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function3;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row3;
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
public class Members extends TableImpl<MembersRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>library_management.members</code>
     */
    public static final Members MEMBERS = new Members();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<MembersRecord> getRecordType() {
        return MembersRecord.class;
    }

    /**
     * The column <code>library_management.members.id</code>.
     */
    public final TableField<MembersRecord, String> ID = createField(DSL.name("id"), SQLDataType.VARCHAR(32).nullable(false), this, "");

    /**
     * The column <code>library_management.members.full_name</code>.
     */
    public final TableField<MembersRecord, String> FULL_NAME = createField(DSL.name("full_name"), SQLDataType.VARCHAR(64).nullable(false), this, "");

    /**
     * The column <code>library_management.members.join_date</code>.
     */
    public final TableField<MembersRecord, LocalDateTime> JOIN_DATE = createField(DSL.name("join_date"), SQLDataType.LOCALDATETIME(0).nullable(false), this, "");

    private Members(Name alias, Table<MembersRecord> aliased) {
        this(alias, aliased, null);
    }

    private Members(Name alias, Table<MembersRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>library_management.members</code> table reference
     */
    public Members(String alias) {
        this(DSL.name(alias), MEMBERS);
    }

    /**
     * Create an aliased <code>library_management.members</code> table reference
     */
    public Members(Name alias) {
        this(alias, MEMBERS);
    }

    /**
     * Create a <code>library_management.members</code> table reference
     */
    public Members() {
        this(DSL.name("members"), null);
    }

    public <O extends Record> Members(Table<O> child, ForeignKey<O, MembersRecord> key) {
        super(child, key, MEMBERS);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : LibraryManagement.LIBRARY_MANAGEMENT;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.MEMBERS_MEMBERS_FULL_NAME_INDEX);
    }

    @Override
    public UniqueKey<MembersRecord> getPrimaryKey() {
        return Keys.KEY_MEMBERS_PRIMARY;
    }

    @Override
    public Members as(String alias) {
        return new Members(DSL.name(alias), this);
    }

    @Override
    public Members as(Name alias) {
        return new Members(alias, this);
    }

    @Override
    public Members as(Table<?> alias) {
        return new Members(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Members rename(String name) {
        return new Members(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Members rename(Name name) {
        return new Members(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Members rename(Table<?> name) {
        return new Members(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<String, String, LocalDateTime> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function3<? super String, ? super String, ? super LocalDateTime, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function3<? super String, ? super String, ? super LocalDateTime, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
