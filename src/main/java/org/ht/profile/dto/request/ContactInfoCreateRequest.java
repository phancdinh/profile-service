package org.ht.profile.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ht.profile.dto.request.internal.AddressContactRequest;
import org.ht.profile.dto.request.internal.HierarchyContactRequest;
import org.ht.profile.validator.constraint.UniquePrimaryContact;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ContactInfoCreateRequest {
    @UniquePrimaryContact(message = "{validation.contact.address.unique}")
    private List<AddressContactRequest> postalAddresses;

    @UniquePrimaryContact(message = "{validation.contact.email.unique}")
    private List<HierarchyContactRequest> emails;

    @UniquePrimaryContact(message = "{validation.contact.phone.unique}")
    private List<HierarchyContactRequest> phoneNumbers;
}
