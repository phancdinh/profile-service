package org.ht.profile.data.model.internal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseIdentityDocument {
    private String number;
    private String fullName;
    private String gender;
    private String nationality;
    private Address pob;
    private Address homeTown;
    private Address permanentAddress;
    private Address issuedPlace;
    private HierarchyDate dob;
    private HierarchyDate issuedDate;
    private HierarchyDate expiryDate;
}
