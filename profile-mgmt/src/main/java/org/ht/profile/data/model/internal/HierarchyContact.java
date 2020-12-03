package org.ht.profile.data.model.internal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ht.common.constant.ContactTag;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HierarchyContact {
    private String value;
    private boolean primary;
    private Set<ContactTag> tags;
}
