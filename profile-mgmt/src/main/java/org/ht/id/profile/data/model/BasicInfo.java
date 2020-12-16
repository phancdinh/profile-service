package org.ht.id.profile.data.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.ht.id.profile.data.model.internal.Address;
import org.ht.id.profile.data.model.internal.HierarchyDate;
import org.ht.id.profile.data.model.internal.UserName;
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
@Builder
@Document(collection = "basic_info")
public class BasicInfo {
    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private ObjectId htCode;

    private String gender;

    private UserName userName;

    private List<String> nationalities;

    private HierarchyDate dob;

    private Address pob;

    private Address hometown;

    private Address permanentAddress;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    private Date createdAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @LastModifiedDate
    private Date lastModifiedDate;
}
