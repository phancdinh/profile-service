package org.ht.profile.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ht.profile.constants.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileUpdateRequest {
    private String userName;
    private String gender;
    private List<String> nationalities;
    @DateTimeFormat(pattern = DateTime.BIRTHDAY_PATTERN)
    private Date dob;
    private String fullName;
    private String pob;
    private String hometown;
    private String permanentAddress;
}
