package org.ht.id.profile.data.model.internal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ht.id.common.constant.ContactTag;

import java.util.Collections;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HierarchyContact {
    public HierarchyContact(String value, boolean primary) {
        this.value = value;
        this.primary = primary;
        this.tags = Collections.emptySet();
    }

    private String value;
    private boolean primary;
    private Set<ContactTag> tags;
    private boolean verified;
}