package org.ht.id.profile.data.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.ht.id.profile.data.model.internal.BaseIdentityDocument;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@Builder
@Document(collection = "legal_info")
public class LegalInfo {
    @Id
    private ObjectId id;
    private ObjectId htCode;
    private BaseIdentityDocument nationalIdentity;
    private BaseIdentityDocument citizenIdentity;
    private List<BaseIdentityDocument> passports;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    private Date createdAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @LastModifiedDate
    private Date lastModifiedDate;
}
