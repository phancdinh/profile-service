package org.ht.profile.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ht.profile.data.model.internal.AddressContact;
import org.ht.profile.data.model.internal.HierarchyContact;
import org.ht.profile.dto.response.internal.AddressContactResponse;
import org.ht.profile.dto.response.internal.HierarchyContactResponse;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ContactInfoResponse {

    private String htId;

    private List<AddressContactResponse> postalAddresses;
    private List<HierarchyContactResponse> emails;
    private List<HierarchyContactResponse> phoneNumbers;

    private Date lastModifiedDate;
}
