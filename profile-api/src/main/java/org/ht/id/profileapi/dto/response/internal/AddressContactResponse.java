package org.ht.id.profileapi.dto.response.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ht.id.common.constant.ContactTag;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AddressContactResponse {
    @JsonProperty("value")
    private String fullAddress;
    private boolean primary;
    private Set<ContactTag> tags;
}
