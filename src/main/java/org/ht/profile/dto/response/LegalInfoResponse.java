package org.ht.profile.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ht.profile.dto.response.internal.CitizenIdentityResponse;
import org.ht.profile.dto.response.internal.NationalIdentityResponse;
import org.ht.profile.dto.response.internal.PassportResponse;
import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LegalInfoResponse {
    private String htId;
    private NationalIdentityResponse nationalIdentity;
    private CitizenIdentityResponse citizenIdentity;
    private ArrayList<PassportResponse> passports;
    private Date lastModifiedDate;
}
