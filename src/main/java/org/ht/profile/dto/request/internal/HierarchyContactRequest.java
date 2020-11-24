package org.ht.profile.dto.request.internal;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.ht.profile.data.model.internal.HierarchyContact;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class HierarchyContactRequest extends HierarchyContact {
}
