package com.cycastic.library_management.services;

import com.cycastic.library_management.generated.tables.*;
import com.cycastic.library_management.generated.tables.records.BooksRecord;
import com.cycastic.library_management.models.IntegerWrapper;
import com.cycastic.library_management.models.author.AuthorDetails;
import com.cycastic.library_management.models.book.BookCreationRequest;
import com.cycastic.library_management.models.book.BookDetails;
import com.cycastic.library_management.models.book.BookUpdateRequest;
import com.cycastic.library_management.models.book.RawBookDetails;
import com.cycastic.library_management.models.shelf.ShelfDetails;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.types.ULong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.Comparator;

@Service
public class BooksService {
    @Autowired
    private DSLContext dsl;
    private final Books books = Books.BOOKS;
    private final Authors authors = Authors.AUTHORS;
    private final BookAuthors bookAuthors = BookAuthors.BOOK_AUTHORS;
    private final Shelves shelves = Shelves.SHELVES;
    private final BookLocations bookLocations = BookLocations.BOOK_LOCATIONS;

    public int createBook(@NonNull BookCreationRequest request){
        final var wrappedId = new IntegerWrapper(0);
        dsl.transaction(configuration -> {
            var transactionalDSL = DSL.using(configuration);
            ArrayList<Integer> authorIds = new ArrayList<>();
            for (var author : request.getAuthors()){
                try {
                    var ret = transactionalDSL.insertInto(authors, authors.NAME)
                            .values(author)
                            .returning(authors.ID)
                            .fetchOne();
                    var aId = Objects.requireNonNull(ret)
                            .getValue(authors.ID);
                    authorIds.add(aId.intValue());
                } catch (DataIntegrityViolationException e){
                    var ret = transactionalDSL.select()
                            .from(authors)
                            .where(DSL.upper(authors.NAME).eq(author.toUpperCase()))
                            .fetchOne();
                    var aId = Objects.requireNonNull(ret).into(AuthorDetails.class).getId();
                    authorIds.add(aId);
                }
            }
            var bookRet = transactionalDSL.insertInto(books, books.TITLE, books.CAPSULE_URL, books.ISBN, books.PUBLISHED_DATE, books.IN_STORAGE)
                    .values(request.getTitle(), request.getCapsuleUrl(), request.getIsbn(), request.getPublishedDate(), request.getInStorage())
                    .returning(books.ID)
                    .fetchOne();
            var bId = Objects.requireNonNull(bookRet).into(RawBookDetails.class).getId();
            wrappedId.setValue(bId);
            for (var aId : authorIds){
                transactionalDSL.insertInto(bookAuthors, bookAuthors.BOOK_ID, bookAuthors.AUTHOR_ID)
                        .values(ULong.valueOf(bId), ULong.valueOf(aId))
                        .execute();
            }
        });
        return wrappedId.getValue();
    }

    public @Nullable RawBookDetails getRawBookDetails(int id){
        var record = dsl.select(books)
                .from(books)
                .where(books.ID.eq(ULong.valueOf(id)))
                .fetchOne();
        return record == null ? null : record.into(RawBookDetails.class);
    }

    public Collection<AuthorDetails> getAssociatedAuthors(int bookId){
        return dsl.select(authors.ID, authors.NAME)
                .from(authors)
                .join(bookAuthors).on(authors.ID.eq(bookAuthors.AUTHOR_ID))
                .where(bookAuthors.BOOK_ID.eq(ULong.valueOf(bookId)))
                .fetchInto(AuthorDetails.class);
    }

    private @NonNull SelectJoinStep<Record7<ULong, String, String, String, LocalDateTime, String, Integer>> getBooksBase(){
        return dsl.select(books.ID, books.TITLE, books.CAPSULE_URL, books.ISBN, books.PUBLISHED_DATE, authors.NAME, DSL.count(bookLocations.ID).as("in_storage"))
                .from(books)
                .innerJoin(bookAuthors).on(books.ID.eq(bookAuthors.BOOK_ID))
                .innerJoin(authors).on(bookAuthors.AUTHOR_ID.eq(authors.ID))
                .leftJoin(bookLocations).on(books.ID.eq(bookLocations.BOOK_ID));
//                .innerJoin(bookAuthors).on(books.ID.eq(bookAuthors.BOOK_ID));
    }

    public Collection<RawBookDetails> getRawAssociatedBooks(@NonNull String authorName){
        return getBooksBase()
                        .where(DSL.upper(authors.NAME).eq(authorName.toUpperCase()))
                        .groupBy(books.ID, books.TITLE, books.CAPSULE_URL, books.ISBN, books.PUBLISHED_DATE, authors.NAME)
                        .fetchInto(RawBookDetails.class);
    }

    private record BookKeyPair(int id, int inStorage){ }

    private @NonNull ArrayList<BookDetails> fulfill(@NonNull Collection<RawBookDetails> rawBookDetails){
        Map<BookKeyPair, BookDetails> map = new TreeMap<>(Comparator.comparingInt(o -> o.id));
        for (var book : rawBookDetails){
            BookDetails fulfilled;
            var key = new BookKeyPair(book.getId(), book.getInStorage());
            if (map.containsKey(key)){
                fulfilled = map.get(key);
            } else {
                fulfilled = book.incorporateAuthors(new ArrayList<>());
            }
            fulfilled.getAuthors().add(book.getName());
            map.put(key, fulfilled);
        }
        return new ArrayList<>(map.values());
    }

    public Collection<BookDetails> getAllBooks(){
        var rawBookDetails = getBooksBase()
                .groupBy(books.ID, books.TITLE, books.CAPSULE_URL, books.ISBN, books.PUBLISHED_DATE, authors.NAME)
                .orderBy(DSL.field("in_storage"))
                .fetchInto(RawBookDetails.class);
        return fulfill(rawBookDetails);
    }

    public Collection<BookDetails> searchBooks(String pattern){
        var rawBookDetails = getBooksBase()
                .where(DSL.upper(books.TITLE).like(String.format("%%%s%%", pattern).toUpperCase()).or(books.ISBN.eq(pattern)))
                .groupBy(books.ID, books.TITLE, books.CAPSULE_URL, books.ISBN, books.PUBLISHED_DATE, authors.NAME)
                .orderBy(DSL.field("in_storage"))
                .limit(10)
                .fetchInto(RawBookDetails.class);
        return fulfill(rawBookDetails);
    }

    public Collection<BookDetails> getAssociatedBooks(String authorName){
        var rawBookDetails = getRawAssociatedBooks(authorName);
        return fulfill(rawBookDetails);
    }

    private BookDetails rectifyBookDetails(@NonNull RawBookDetails rawBookDetails){
        ArrayList<String> authorNames = new ArrayList<>();
        var authorRecords = getAssociatedAuthors(rawBookDetails.getId());
        for (var author : authorRecords){
            authorNames.add(author.getName());
        }
        return rawBookDetails.incorporateAuthorName(authorNames);
    }

    public @Nullable BookDetails getBook(int id){
        var rawBookDetails = getRawBookDetails(id);
        if (rawBookDetails == null) return null;
        return rectifyBookDetails(rawBookDetails);
    }

    public Collection<BookDetails> getBooksByIsbn(String isbn){
        var rawBookDetails = dsl.select()
                .from(books)
                .where(books.ISBN.eq(isbn))
                .fetchInto(RawBookDetails.class);
        ArrayList<BookDetails> books = new ArrayList<>();
        for (var rawBook : rawBookDetails){
            books.add(rectifyBookDetails(rawBook));
        }
        return books;
    }

    public int countBooks(){
        return dsl.fetchCount(books);
    }

    public int addToShelf(int bookId, String shelfId) {
        return dsl.insertInto(bookLocations, bookLocations.BOOK_ID, bookLocations.SHELF_ID)
                .values(ULong.valueOf(bookId), shelfId)
                .execute();
    }

    private org.jooq.SelectConditionStep<org.jooq.Record2<String, String>> selectShelvesByBook(int bookId){
        return dsl.select(shelves.ID, shelves.SHELF_NAME)
                .from(books)
                .innerJoin(bookLocations)
                .on(bookLocations.BOOK_ID.eq(books.ID))
                .innerJoin(shelves)
                .on(shelves.ID.eq(bookLocations.SHELF_ID))
                .where(books.ID.eq(ULong.valueOf(bookId)));
    }

    public int countCopy(int bookId){
        return dsl.fetchCount(selectShelvesByBook(bookId));
    }

    public Collection<ShelfDetails> getContainingShelves(int bookId){
        return selectShelvesByBook(bookId).fetchInto(ShelfDetails.class);
    }

    public void updateBook(@NonNull BookUpdateRequest request){
        UpdateSetStep<BooksRecord> builder = dsl.update(books);
        if (request.getTitle() != null){
            builder = builder.set(books.TITLE, request.getTitle());
        }
        if (request.getCapsuleUrl() != null){
            builder = builder.set(books.CAPSULE_URL, request.getCapsuleUrl());
        }
        if (request.getIsbn() != null){
            builder = builder.set(books.ISBN, request.getIsbn());
        }
        if (request.getPublishedDate() != null){
            builder = builder.set(books.PUBLISHED_DATE, request.getPublishedDate());
        }
        if (request.getInStorage() != null){
            builder = builder.set(books.IN_STORAGE, request.getInStorage());
        }
        var classDetail = UpdateWhereStep.class;
        classDetail.cast(builder).where(books.ID.eq(ULong.valueOf(request.getId()))).execute();
    }
    public int deleteBook(int id){
        return Arrays.stream(dsl.batch(dsl.deleteFrom(bookAuthors).where(bookAuthors.BOOK_ID.eq(ULong.valueOf(id))),
                dsl.deleteFrom(books).where(books.ID.eq(ULong.valueOf(id))))
                .execute()).sum();
    }
}
