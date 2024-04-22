package com.cycastic.library_management.services;

import com.cycastic.library_management.generated.tables.BorrowedBooks;
import com.cycastic.library_management.generated.tables.Members;
import com.cycastic.library_management.models.member.MemberCreationRequest;
import com.cycastic.library_management.models.member.MemberDetails;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

@Service
public class MembersService {
    @Autowired
    private DSLContext dsl;
    private final Members members = Members.MEMBERS;
    private final BorrowedBooks borrowedBooks = BorrowedBooks.BORROWED_BOOKS;

    public String createMember(@NonNull MemberCreationRequest request){
        var currentDateTime = LocalDateTime.now();
        dsl.insertInto(members, members.ID, members.FULL_NAME, members.JOIN_DATE)
                .values(request.getId(), request.getFullName(), currentDateTime)
                .execute();
        return request.getId();
    }

    public @Nullable MemberDetails getMember(String id){
        var record = dsl.select()
                .from(members)
                .where(members.ID.eq(id))
                .fetchOne();
        return record == null ? null : record.into(MemberDetails.class);
    }

    public @Nullable Collection<MemberDetails> searchMember(String pattern){
        return dsl.select()
                .from(members)
                .where(DSL.upper(members.FULL_NAME).like(String.format("%%%s%%", pattern).toUpperCase())
                        .or(members.ID.eq(pattern)))
                .orderBy(members.FULL_NAME)
                .limit(10)
                .fetchInto(MemberDetails.class);
    }

    public int countMember(){
        return dsl.fetchCount(members);
    }

    public void updateMember(@NonNull MemberCreationRequest request){
        if (request.getFullName() == null) return;
        dsl.update(members)
                .set(members.FULL_NAME, request.getFullName())
                .where(members.ID.eq(request.getId())).execute();
    }

    public int deleteMember(String id){
        var batch = dsl.batch(dsl.deleteFrom(borrowedBooks).where(borrowedBooks.MEMBER_ID.eq(id)),
                dsl.deleteFrom(members).where(members.ID.eq(id)));
        return Arrays.stream(batch.execute()).sum();
    }
}
