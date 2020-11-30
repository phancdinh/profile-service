package org.ht.profile.data.model.internal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Address {
    private String fullAddress;
    private String country;
    private String city;
    private String district;
    private String ward;
    private String other;

    public Address(String fullAddress) {
        this.fullAddress = fullAddress;
    }
}
