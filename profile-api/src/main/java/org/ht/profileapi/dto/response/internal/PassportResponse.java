package org.ht.profileapi.dto.response.internal;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PassportResponse extends BaseIdentityDocumentResponse {
    private String pob;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date expiryDate;
}
