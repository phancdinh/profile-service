package org.ht.profile.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ht.profile.data.model.BasicInfo;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

import static org.ht.profile.constants.DateTime.DATE_PATTERN;


@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BasicInfoResponse {

    public BasicInfoResponse(BasicInfo basicInfo, String htId) {
        BeanUtils.copyProperties(basicInfo, this);
        this.htId = htId;
        this.hometown = basicInfo.getHometown().getFullAddress();
        this.permanentAddress = basicInfo.getPermanentAddress().getFullAddress();
        this.pob = basicInfo.getPob().getFullAddress();
        this.dob = basicInfo.getDob().getFullDate();
        this.fullName = basicInfo.getUserName().getFullName();
    }

    private String htId;

    private String gender;

    private List<String> nationalities;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    private Date dob;

    private String fullName;

    private String pob;

    private String hometown;

    private String permanentAddress;

    private Date lastModifiedDate;
}
