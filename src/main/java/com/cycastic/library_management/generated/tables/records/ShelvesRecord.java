/*
 * This file is generated by jOOQ.
 */
package com.cycastic.library_management.generated.tables.records;


import com.cycastic.library_management.generated.tables.Shelves;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ShelvesRecord extends UpdatableRecordImpl<ShelvesRecord> implements Record2<String, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>library_management.shelves.id</code>.
     */
    public void setId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>library_management.shelves.id</code>.
     */
    public String getId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>library_management.shelves.shelf_name</code>.
     */
    public void setShelfName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>library_management.shelves.shelf_name</code>.
     */
    public String getShelfName() {
        return (String) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row2<String, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<String, String> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return Shelves.SHELVES.ID;
    }

    @Override
    public Field<String> field2() {
        return Shelves.SHELVES.SHELF_NAME;
    }

    @Override
    public String component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getShelfName();
    }

    @Override
    public String value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getShelfName();
    }

    @Override
    public ShelvesRecord value1(String value) {
        setId(value);
        return this;
    }

    @Override
    public ShelvesRecord value2(String value) {
        setShelfName(value);
        return this;
    }

    @Override
    public ShelvesRecord values(String value1, String value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ShelvesRecord
     */
    public ShelvesRecord() {
        super(Shelves.SHELVES);
    }

    /**
     * Create a detached, initialised ShelvesRecord
     */
    public ShelvesRecord(String id, String shelfName) {
        super(Shelves.SHELVES);

        setId(id);
        setShelfName(shelfName);
    }

    /**
     * Create a detached, initialised ShelvesRecord
     */
    public ShelvesRecord(com.cycastic.library_management.generated.tables.pojos.Shelves value) {
        super(Shelves.SHELVES);

        if (value != null) {
            setId(value.getId());
            setShelfName(value.getShelfName());
        }
    }
}
