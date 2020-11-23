package org.ht.profile.data.model.internal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String fullAddress;
    private String country;
    private String city;
    private String district;
    private String ward;
    private String other;
}
