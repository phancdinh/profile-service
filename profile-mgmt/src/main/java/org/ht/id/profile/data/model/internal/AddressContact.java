package org.ht.id.profile.data.model.internal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.ht.id.common.constant.ContactTag;

import java.util.Set;

@Getter
@Setter
public class AddressContact extends Address {
    private boolean primary;
    private Set<ContactTag> tags;

    @Builder(builderMethodName = "addressContactBuilder")
    public AddressContact(String fullAddress, String country, String city, String district, String ward, String other, boolean primary, Set<ContactTag> tags) {
        super(fullAddress, country, city, district, ward, other);
        this.primary = primary;
        this.tags = tags;
    }
}
