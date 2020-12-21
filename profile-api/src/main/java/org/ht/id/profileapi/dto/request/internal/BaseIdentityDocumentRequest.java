package org.ht.id.profileapi.dto.request.internal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ht.id.common.constant.Nationalities;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseIdentityDocumentRequest {
    private String number;
    private String fullName;
    private String gender;
    private String nationality = Nationalities.VIETNAMESE;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date dob;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date issuedDate;

    private String issuedPlace;

    public void setNationality(String nationality) {
        this.nationality = StringUtils.isEmpty(nationality) ? Nationalities.VIETNAMESE : nationality;
    }
}
