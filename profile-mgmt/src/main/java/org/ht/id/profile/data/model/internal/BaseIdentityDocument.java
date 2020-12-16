package org.ht.id.profile.data.model.internal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
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
