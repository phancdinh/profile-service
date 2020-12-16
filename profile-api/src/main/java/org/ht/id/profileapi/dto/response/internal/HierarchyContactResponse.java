package org.ht.id.profileapi.dto.response.internal;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ht.id.common.constant.ContactTag;

import java.util.Set;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Builder
public class HierarchyContactResponse {
    private String value;
    private boolean primary;
    private Set<ContactTag> tags;
    private boolean verified;
}
