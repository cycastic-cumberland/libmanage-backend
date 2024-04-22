package com.cycastic.library_management.services;

import com.cycastic.library_management.generated.tables.BookLocations;
import com.cycastic.library_management.generated.tables.Shelves;
import com.cycastic.library_management.models.shelf.BookLocationCreationRequest;
import com.cycastic.library_management.models.shelf.BookLocationDetails;
import com.cycastic.library_management.models.shelf.ShelfDetails;
import com.cycastic.library_management.models.shelf.ShelfInfo;
import org.jooq.DSLContext;
import org.jooq.Record3;
import org.jooq.SelectOnConditionStep;
import org.jooq.impl.DSL;
import org.jooq.types.ULong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;

@Service
public class ShelvesService {
    @Autowired
    private DSLContext dsl;
    private final Shelves shelves = Shelves.SHELVES;
    private static final BookLocations bookLocations = BookLocations.BOOK_LOCATIONS;
    public String createShelf(@NonNull ShelfDetails request){
        dsl.insertInto(shelves, shelves.ID, shelves.SHELF_NAME)
                .values(request.getId(), request.getShelfName())
                .execute();
        return request.getId();
    }

    public @Nullable ShelfDetails getShelf(String id){
        var record = dsl.select()
                .from(shelves)
                .where(shelves.ID.eq(id))
                .fetchOne();
        return record == null ? null : record.into(ShelfDetails.class);
    }

    public @NonNull Collection<ShelfDetails> getAllShelves(){
        return dsl.select()
                .from(shelves)
                .fetchInto(ShelfDetails.class);
    }

    public void updateShelf(@NonNull ShelfDetails request){
        if (request.getShelfName() == null) return;
        dsl.update(shelves)
                .set(shelves.SHELF_NAME, request.getShelfName())
                .where(shelves.ID.eq(request.getId())).execute();
    }

//    public int addOneBookToShelf(@NonNull BookLocationCreationRequest request){
//        var record = dsl.insertInto(bookLocations, bookLocations.BOOK_ID, bookLocations.SHELF_ID)
//                .values(ULong.valueOf(request.getBookId()), request.getShelfId())
//                .returning(bookLocations.ID)
//                .fetchOne();
//        return record == null ? 0 : record.into(BookLocationDetails.class).getId();
//    }

    private @NonNull SelectOnConditionStep<Record3<String, String, Integer>> shelvesInfoInternal(){
        return dsl.select(shelves.ID, shelves.SHELF_NAME, DSL.count(bookLocations.ID).as("book_count"))
                .from(shelves)
                .leftJoin(bookLocations).on(bookLocations.SHELF_ID.eq(shelves.ID));
    }

    public Collection<ShelfInfo> getShelvesInfo() {
        return shelvesInfoInternal()
                .groupBy(shelves.ID, shelves.SHELF_NAME)
                .fetchInto(ShelfInfo.class);
    }

    public Collection<ShelfInfo> searchShelvesInfo(String pattern) {
        return shelvesInfoInternal()
                .where(DSL.upper(shelves.SHELF_NAME).like(String.format("%%%s%%", pattern).toUpperCase()).or(shelves.ID.eq(pattern)))
                .groupBy(shelves.ID, shelves.SHELF_NAME)
                .fetchInto(ShelfInfo.class);
    }

    public static int removeOneBookFromShelf(@NonNull DSLContext dsl, @NonNull BookLocationCreationRequest request){
        return dsl.deleteFrom(bookLocations)
                .where(bookLocations.ID.in(
                        dsl.select(bookLocations.ID)
                                .from(bookLocations)
                                .where(bookLocations.BOOK_ID.eq(ULong.valueOf(request.getBookId()))
                                        .and(bookLocations.SHELF_ID.eq(request.getShelfId())))
                                .limit(1))).execute();
    }

//    public int removeOneBookFromShelf(@NonNull BookLocationCreationRequest request){
//        return removeOneBookFromShelf(dsl, request);
//    }

    public int deleteShelf(String id){
        var batch = dsl.batch(dsl.deleteFrom(bookLocations).where(bookLocations.SHELF_ID.eq(id)),
                dsl.deleteFrom(shelves).where(shelves.ID.eq(id)))
                .execute();
        return Arrays.stream(batch).sum();
    }
}
