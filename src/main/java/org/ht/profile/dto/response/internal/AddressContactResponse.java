package org.ht.profile.dto.response.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import org.ht.profile.constants.ContactTag;
import org.ht.profile.data.model.internal.AddressContact;

import java.util.Set;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AddressContactResponse {

    public AddressContactResponse(AddressContact addressContact) {
        this.fullAddress = addressContact.getFullAddress();
        this.primary = addressContact.isPrimary();
        this.tags = addressContact.getTags();
    }

    @JsonProperty("value")
    private String fullAddress;
    private boolean primary;
    private Set<ContactTag> tags;

}
