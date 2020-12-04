package org.ht.account.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.ht.account.data.model.internal.Activation;
import org.ht.account.data.model.internal.Invitation;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "account")
public class Account {
    @Id
    @ToString.Exclude
    private ObjectId id;

    @Indexed(unique = true)
    private String htId;

    private String email;

    private String phone;

    @Transient
    private String password;

    private boolean active;

    private boolean userCreated;
    
    @ToString.Exclude
    private Activation activation;

    @ToString.Exclude
    private List<Invitation> invitations;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    private Date createdAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @LastModifiedDate
    private Date lastModifiedDate;
}

