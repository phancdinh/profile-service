package org.ht.profile.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ht.profile.dto.request.internal.CitizenIdentityRequest;
import org.ht.profile.dto.request.internal.NationalIdentityRequest;
import org.ht.profile.dto.request.internal.PassportRequest;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LegalInfoCreateRequest {
    private NationalIdentityRequest nationalIdentity;
    private CitizenIdentityRequest citizenIdentity;
    private ArrayList<PassportRequest> passports;
}
