package org.ht.profile.dto;

import lombok.Getter;
import lombok.Setter;
import org.ht.profile.dto.internal.AddressDto;
import org.ht.profile.dto.internal.HierarchyDateDto;
import org.ht.profile.dto.internal.UserNameDto;

import java.util.List;

@Getter
@Setter
public class BasicInfoDto extends BaseDto{
    private String gender;

    private UserNameDto userName;

    private List<String> nationalities;

    private HierarchyDateDto dob;

    private AddressDto pob;

    private AddressDto hometown;

    private AddressDto permanentAddress;
}
