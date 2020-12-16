package org.ht.id.profile.data.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.ht.id.profile.data.model.internal.AddressContact;
import org.ht.id.profile.data.model.internal.HierarchyContact;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Document(collection = "contact_info")
@Builder
public class ContactInfo {
    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private ObjectId htCode;

    private List<AddressContact> postalAddresses;
    private List<HierarchyContact> emails;
    private List<HierarchyContact> phoneNumbers;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    private Date createdAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @LastModifiedDate
    private Date lastModifiedDate;
}
