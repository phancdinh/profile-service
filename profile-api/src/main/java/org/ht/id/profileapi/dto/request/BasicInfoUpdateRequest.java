package org.ht.id.profileapi.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ht.id.common.constant.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BasicInfoUpdateRequest {
    private String gender;

    private List<String> nationalities;

    @DateTimeFormat(pattern = DateTime.DATE_PATTERN)
    private Date dob;

    private String fullName;

    private String pob;

    private String hometown;

    private String permanentAddress;
}
