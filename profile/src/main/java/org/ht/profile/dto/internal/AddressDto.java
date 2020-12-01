package org.ht.profile.dto.internal;

import lombok.Getter;
import lombok.Setter;
import org.ht.profile.dto.BaseDto;

@Getter
@Setter
public class AddressDto extends BaseDto {
    private String fullAddress;
    private String country;
    private String city;
    private String district;
    private String ward;
    private String other;
}
