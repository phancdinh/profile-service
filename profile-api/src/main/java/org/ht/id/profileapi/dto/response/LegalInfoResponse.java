package org.ht.id.profileapi.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ht.id.profileapi.dto.response.internal.CitizenIdentityResponse;
import org.ht.id.profileapi.dto.response.internal.NationalIdentityResponse;
import org.ht.id.profileapi.dto.response.internal.PassportResponse;

import java.util.ArrayList;
import java.util.Date;

import static org.ht.id.common.constant.DateTime.DATETIME_PATTERN;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATETIME_PATTERN)
    private Date lastModifiedDate;
}