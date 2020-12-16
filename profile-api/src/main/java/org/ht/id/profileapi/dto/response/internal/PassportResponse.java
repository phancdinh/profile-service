package org.ht.id.profileapi.dto.response.internal;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PassportResponse extends BaseIdentityDocumentResponse {
    private String pob;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date expiryDate;

    @Builder(builderMethodName = "passportResponseBuilder")
    public PassportResponse(String number, String fullName, String gender, String nationality, String issuedPlace, Date dob, Date issuedDate, String pob, Date expiryDate) {
        super(number, fullName, gender, nationality, issuedPlace, dob, issuedDate);
        this.pob = pob;
        this.expiryDate = expiryDate;
    }
}
