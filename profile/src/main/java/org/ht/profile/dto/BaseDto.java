package org.ht.profile.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public abstract class BaseDto {
    private String htId;

    private Date createdAt;
    private Date lastModifiedDate;
}
