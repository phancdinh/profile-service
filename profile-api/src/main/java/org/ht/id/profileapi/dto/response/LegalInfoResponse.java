package org.ht.id.profileapi.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.ht.id.profileapi.dto.response.internal.CitizenIdentityResponse;
import org.ht.id.profileapi.dto.response.internal.NationalIdentityResponse;
import org.ht.id.profileapi.dto.response.internal.PassportResponse;

import java.util.Date;
import java.util.List;

import static org.ht.id.common.constant.DateTime.DATETIME_PATTERN;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Builder
public class LegalInfoResponse {
    private String htId;
    private NationalIdentityResponse nationalIdentity;
    private CitizenIdentityResponse citizenIdentity;
    private List<PassportResponse> passports;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATETIME_PATTERN)
    private Date lastModifiedDate;
}
