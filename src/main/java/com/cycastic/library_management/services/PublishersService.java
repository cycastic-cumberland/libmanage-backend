package com.cycastic.library_management.services;

import com.cycastic.library_management.generated.tables.Publisher;
import com.cycastic.library_management.generated.tables.records.PublisherRecord;
import com.cycastic.library_management.models.publisher.PublisherCreationRequest;
import com.cycastic.library_management.models.publisher.PublisherDetails;
import com.cycastic.library_management.models.publisher.PublisherUpdateRequest;
import org.jooq.DSLContext;
import org.jooq.UpdateSetStep;
import org.jooq.UpdateWhereStep;
import org.jooq.types.ULong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;


@Service
public class PublishersService {
    @Autowired
    private DSLContext dsl;
    private final Publisher publisher = Publisher.PUBLISHER;

    public int createPublisher(@NonNull PublisherCreationRequest request){
        var record = dsl.insertInto(publisher, publisher.EMAIL, publisher.PUBLISHER_NAME)
                .values(request.getEmail(), request.getPublisherName())
                .returning(publisher.ID)
                .fetchOne();
        return record == null ? 0 : record.getValue(publisher.ID).intValue();
    }

    public @Nullable PublisherDetails getPublisher(int id){
        var record = dsl.select()
                .from(publisher)
                .where(publisher.ID.eq(ULong.valueOf(id)))
                .fetchOne();
        return record == null ? null : record.into(PublisherDetails.class);
    }

    public void updatePublisher(@NonNull PublisherUpdateRequest request){
        UpdateSetStep<PublisherRecord> builder = dsl.update(publisher);
        if (request.getEmail() != null){
            builder = builder.set(publisher.EMAIL, request.getEmail());
        }
        if (request.getPublisherName() != null){
            builder = builder.set(publisher.PUBLISHER_NAME, request.getPublisherName());
        }
        var classDetail = UpdateWhereStep.class;
        classDetail.cast(builder).where(publisher.ID.eq(ULong.valueOf(request.getId()))).execute();
    }
    public int deletePublisher(int id){
        return dsl.deleteFrom(publisher)
                .where(publisher.ID.eq(ULong.valueOf(id)))
                .execute();
    }
}
