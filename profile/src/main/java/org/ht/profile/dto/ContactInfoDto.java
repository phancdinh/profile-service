package org.ht.profile.dto;

import lombok.Getter;
import lombok.Setter;
import org.ht.profile.dto.internal.AddressContactDto;
import org.ht.profile.dto.internal.HierarchyContactDto;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ContactInfoDto extends BaseDto {
    private List<AddressContactDto> postalAddresses;
    private List<HierarchyContactDto> emails;
    private List<HierarchyContactDto> phoneNumbers;

    private Date createdAt;
    private Date lastModifiedDate;
}
