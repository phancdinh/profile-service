package org.ht.profile.dto.internal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ht.profile.constants.ContactTag;
import org.ht.profile.dto.BaseDto;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HierarchyContactDto extends BaseDto {
    private String value;
    private boolean primary;
    private Set<ContactTag> tags;
}
