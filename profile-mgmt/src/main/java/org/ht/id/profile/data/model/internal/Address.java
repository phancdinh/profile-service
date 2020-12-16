package org.ht.id.profile.data.model.internal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Address {
    private String fullAddress;
    private String country;
    private String city;
    private String district;
    private String ward;
    private String other;
}
