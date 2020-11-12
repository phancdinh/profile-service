package org.ht.profile.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

import static org.ht.profile.constants.DateTime.BIRTHDAY_PATTERN;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class Profile {
    @Id
    private String id;

    @Indexed(unique = true)
    private String htId;

    @Indexed(unique = true)
    private String userName;

    @JsonIgnore
    private String pinCode;

    @JsonIgnore
    private String password;

    private String gender;
    private String fullName;

    private List<String> nationalities;

    @DateTimeFormat(pattern = BIRTHDAY_PATTERN)
    private Date dob;
    private String pob;

    private String hometown;
    private String permanentAddress;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    private Date createdAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @LastModifiedDate
    private Date lastModifiedDate;

}
