package org.ht.id.profileapi.dto.response.internal;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CitizenIdentityResponse extends BaseIdentityDocumentResponse {
    private String homeTown;
    private String permanentAddress;

    @Builder(builderMethodName = "citizenIdentityResponseBuilder")
    public CitizenIdentityResponse(String number, String fullName, String gender, String nationality, String issuedPlace, Date dob, Date issuedDate, String homeTown, String permanentAddress) {
        super(number, fullName, gender, nationality, issuedPlace, dob, issuedDate);
        this.homeTown = homeTown;
        this.permanentAddress = permanentAddress;
    }
}
