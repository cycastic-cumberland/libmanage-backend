/*
 * This file is generated by jOOQ.
 */
package com.cycastic.library_management.generated.tables.records;


import com.cycastic.library_management.generated.tables.Publisher;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.ULong;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PublisherRecord extends UpdatableRecordImpl<PublisherRecord> implements Record3<ULong, String, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>library_management.publisher.id</code>.
     */
    public void setId(ULong value) {
        set(0, value);
    }

    /**
     * Getter for <code>library_management.publisher.id</code>.
     */
    public ULong getId() {
        return (ULong) get(0);
    }

    /**
     * Setter for <code>library_management.publisher.publisher_name</code>.
     */
    public void setPublisherName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>library_management.publisher.publisher_name</code>.
     */
    public String getPublisherName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>library_management.publisher.email</code>.
     */
    public void setEmail(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>library_management.publisher.email</code>.
     */
    public String getEmail() {
        return (String) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<ULong> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<ULong, String, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<ULong, String, String> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<ULong> field1() {
        return Publisher.PUBLISHER.ID;
    }

    @Override
    public Field<String> field2() {
        return Publisher.PUBLISHER.PUBLISHER_NAME;
    }

    @Override
    public Field<String> field3() {
        return Publisher.PUBLISHER.EMAIL;
    }

    @Override
    public ULong component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getPublisherName();
    }

    @Override
    public String component3() {
        return getEmail();
    }

    @Override
    public ULong value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getPublisherName();
    }

    @Override
    public String value3() {
        return getEmail();
    }

    @Override
    public PublisherRecord value1(ULong value) {
        setId(value);
        return this;
    }

    @Override
    public PublisherRecord value2(String value) {
        setPublisherName(value);
        return this;
    }

    @Override
    public PublisherRecord value3(String value) {
        setEmail(value);
        return this;
    }

    @Override
    public PublisherRecord values(ULong value1, String value2, String value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached PublisherRecord
     */
    public PublisherRecord() {
        super(Publisher.PUBLISHER);
    }

    /**
     * Create a detached, initialised PublisherRecord
     */
    public PublisherRecord(ULong id, String publisherName, String email) {
        super(Publisher.PUBLISHER);

        setId(id);
        setPublisherName(publisherName);
        setEmail(email);
    }

    /**
     * Create a detached, initialised PublisherRecord
     */
    public PublisherRecord(com.cycastic.library_management.generated.tables.pojos.Publisher value) {
        super(Publisher.PUBLISHER);

        if (value != null) {
            setId(value.getId());
            setPublisherName(value.getPublisherName());
            setEmail(value.getEmail());
        }
    }
}
