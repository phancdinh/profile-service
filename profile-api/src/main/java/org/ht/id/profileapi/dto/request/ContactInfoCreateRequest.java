package org.ht.id.profileapi.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ht.id.profileapi.validator.constraint.UniqueContactValue;
import org.ht.id.profileapi.dto.request.internal.AddressContactRequest;
import org.ht.id.profileapi.dto.request.internal.HierarchyContactRequest;
import org.ht.id.profileapi.validator.constraint.UniquePrimaryContact;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ContactInfoCreateRequest {
    @UniquePrimaryContact(message = "{validation.contact.address.primary.unique}")
    private List<AddressContactRequest> postalAddresses;


    @UniquePrimaryContact(message = "{validation.contact.email.primary.unique}")
    @UniqueContactValue(message = "{validation.contact.email.unique}")
    private List<HierarchyContactRequest> emails;

    @UniqueContactValue(message = "{validation.contact.phone.unique}")
    @UniquePrimaryContact(message = "{validation.contact.phone.primary.unique}")
    private List<HierarchyContactRequest> phoneNumbers;
}
