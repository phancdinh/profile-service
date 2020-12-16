package org.ht.id.profileapi.dto.response.internal;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ht.id.common.constant.ContactTag;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class NationalIdentityResponse extends BaseIdentityDocumentResponse {
    private String homeTown;
    private String permanentAddress;

    @Builder(builderMethodName = "nationalIdentityResponseBuilder")
    public NationalIdentityResponse(String number, String fullName, String gender, String nationality, String issuedPlace, Date dob, Date issuedDate, String homeTown, String permanentAddress) {
        super(number, fullName, gender, nationality, issuedPlace, dob, issuedDate);
        this.homeTown = homeTown;
        this.permanentAddress = permanentAddress;
    }
}
