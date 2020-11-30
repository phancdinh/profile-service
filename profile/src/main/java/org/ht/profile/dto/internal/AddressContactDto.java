package org.ht.profile.dto.internal;

import lombok.Getter;
import lombok.Setter;
import org.ht.profile.constants.ContactTag;

import java.util.Set;

@Getter
@Setter
public class AddressContactDto extends AddressDto {
    private boolean primary;
    private Set<ContactTag> tags;
}
