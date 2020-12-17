package org.ht.id.profileapi.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ht.id.profileapi.dto.response.internal.AddressContactResponse;
import org.ht.id.profileapi.dto.response.internal.HierarchyContactResponse;

import java.util.Date;
import java.util.List;

import static org.ht.id.common.constant.DateTime.DATETIME_PATTERN;

@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ContactInfoResponse {

    private String htId;

    private List<AddressContactResponse> postalAddresses;
    private List<HierarchyContactResponse> emails;
    private List<HierarchyContactResponse> phoneNumbers;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATETIME_PATTERN)
    private Date lastModifiedDate;
}
