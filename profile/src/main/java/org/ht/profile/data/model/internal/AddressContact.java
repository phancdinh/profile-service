package org.ht.profile.data.model.internal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ht.profile.constants.ContactTag;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressContact extends Address {
    private boolean primary;
    private Set<ContactTag> tags;
}
