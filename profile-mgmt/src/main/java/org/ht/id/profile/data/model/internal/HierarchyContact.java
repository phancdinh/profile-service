package org.ht.id.profile.data.model.internal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.ht.id.common.constant.ContactTag;

import java.util.Collections;
import java.util.Set;

@Getter
@Setter
@Builder
public class HierarchyContact {
    private String value;
    private boolean primary;
    private Set<ContactTag> tags;
    private boolean verified;
}
