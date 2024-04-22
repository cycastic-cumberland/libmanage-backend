/*
 * This file is generated by jOOQ.
 */
package com.cycastic.library_management.generated.tables.daos;


import com.cycastic.library_management.generated.tables.PurchaseBatches;
import com.cycastic.library_management.generated.tables.records.PurchaseBatchesRecord;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.jooq.types.ULong;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PurchaseBatchesDao extends DAOImpl<PurchaseBatchesRecord, com.cycastic.library_management.generated.tables.pojos.PurchaseBatches, ULong> {

    /**
     * Create a new PurchaseBatchesDao without any configuration
     */
    public PurchaseBatchesDao() {
        super(PurchaseBatches.PURCHASE_BATCHES, com.cycastic.library_management.generated.tables.pojos.PurchaseBatches.class);
    }

    /**
     * Create a new PurchaseBatchesDao with an attached configuration
     */
    public PurchaseBatchesDao(Configuration configuration) {
        super(PurchaseBatches.PURCHASE_BATCHES, com.cycastic.library_management.generated.tables.pojos.PurchaseBatches.class, configuration);
    }

    @Override
    public ULong getId(com.cycastic.library_management.generated.tables.pojos.PurchaseBatches object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<com.cycastic.library_management.generated.tables.pojos.PurchaseBatches> fetchRangeOfId(ULong lowerInclusive, ULong upperInclusive) {
        return fetchRange(PurchaseBatches.PURCHASE_BATCHES.ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.cycastic.library_management.generated.tables.pojos.PurchaseBatches> fetchById(ULong... values) {
        return fetch(PurchaseBatches.PURCHASE_BATCHES.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.cycastic.library_management.generated.tables.pojos.PurchaseBatches fetchOneById(ULong value) {
        return fetchOne(PurchaseBatches.PURCHASE_BATCHES.ID, value);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public Optional<com.cycastic.library_management.generated.tables.pojos.PurchaseBatches> fetchOptionalById(ULong value) {
        return fetchOptional(PurchaseBatches.PURCHASE_BATCHES.ID, value);
    }

    /**
     * Fetch records that have <code>purchase_date BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<com.cycastic.library_management.generated.tables.pojos.PurchaseBatches> fetchRangeOfPurchaseDate(LocalDateTime lowerInclusive, LocalDateTime upperInclusive) {
        return fetchRange(PurchaseBatches.PURCHASE_BATCHES.PURCHASE_DATE, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>purchase_date IN (values)</code>
     */
    public List<com.cycastic.library_management.generated.tables.pojos.PurchaseBatches> fetchByPurchaseDate(LocalDateTime... values) {
        return fetch(PurchaseBatches.PURCHASE_BATCHES.PURCHASE_DATE, values);
    }

    /**
     * Fetch records that have <code>from_publisher BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<com.cycastic.library_management.generated.tables.pojos.PurchaseBatches> fetchRangeOfFromPublisher(ULong lowerInclusive, ULong upperInclusive) {
        return fetchRange(PurchaseBatches.PURCHASE_BATCHES.FROM_PUBLISHER, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>from_publisher IN (values)</code>
     */
    public List<com.cycastic.library_management.generated.tables.pojos.PurchaseBatches> fetchByFromPublisher(ULong... values) {
        return fetch(PurchaseBatches.PURCHASE_BATCHES.FROM_PUBLISHER, values);
    }

    /**
     * Fetch records that have <code>cost BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<com.cycastic.library_management.generated.tables.pojos.PurchaseBatches> fetchRangeOfCost(Long lowerInclusive, Long upperInclusive) {
        return fetchRange(PurchaseBatches.PURCHASE_BATCHES.COST, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>cost IN (values)</code>
     */
    public List<com.cycastic.library_management.generated.tables.pojos.PurchaseBatches> fetchByCost(Long... values) {
        return fetch(PurchaseBatches.PURCHASE_BATCHES.COST, values);
    }

    /**
     * Fetch records that have <code>approved_by BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<com.cycastic.library_management.generated.tables.pojos.PurchaseBatches> fetchRangeOfApprovedBy(String lowerInclusive, String upperInclusive) {
        return fetchRange(PurchaseBatches.PURCHASE_BATCHES.APPROVED_BY, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>approved_by IN (values)</code>
     */
    public List<com.cycastic.library_management.generated.tables.pojos.PurchaseBatches> fetchByApprovedBy(String... values) {
        return fetch(PurchaseBatches.PURCHASE_BATCHES.APPROVED_BY, values);
    }
}
