package org.ht.profile.dto.internal;

import lombok.Getter;
import lombok.Setter;
import org.ht.profile.dto.BaseDto;

@Getter
@Setter
public class BaseIdentityDocumentDto extends BaseDto {
    private String number;
    private String fullName;
    private String gender;
    private String nationality;
    private AddressDto pob;
    private AddressDto homeTown;
    private AddressDto permanentAddress;
    private AddressDto issuedPlace;
    private HierarchyDateDto dob;
    private HierarchyDateDto issuedDate;
    private HierarchyDateDto expiryDate;
}
