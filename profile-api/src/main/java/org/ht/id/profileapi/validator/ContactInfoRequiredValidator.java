package org.ht.id.profileapi.validator;

import lombok.extern.slf4j.Slf4j;
import org.ht.id.profileapi.dto.request.ContactInfoCreateRequest;
import org.ht.id.profileapi.dto.request.internal.HierarchyContactRequest;
import org.ht.id.profileapi.validator.constraint.ContactInfoRequired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

@Slf4j
public class ContactInfoRequiredValidator implements ConstraintValidator<ContactInfoRequired, ContactInfoCreateRequest> {
    @Override
    public boolean isValid(ContactInfoCreateRequest contactInfoCreateRequest, ConstraintValidatorContext constraintValidatorContext) {
        return Optional.ofNullable(contactInfoCreateRequest)
                .map(contact -> {
                    long emailCount = Optional.ofNullable(contact.getEmails())
                            .map(emails -> emails
                                    .stream()
                                    .filter(HierarchyContactRequest::isPrimary)
                                    .count()
                            ).orElse(0L);
                    long phoneCount = Optional.ofNullable(contact.getPhoneNumbers())
                            .map(phones -> phones
                                    .stream()
                                    .filter(HierarchyContactRequest::isPrimary)
                                    .count()
                            ).orElse(0L);
                    long postalAddressCount = Optional.ofNullable(contact.getPhoneNumbers())
                            .map(address -> address
                                    .stream()
                                    .filter(HierarchyContactRequest::isPrimary)
                                    .count()
                            ).orElse(0L);
                    return !(emailCount == 0 && phoneCount == 0 && postalAddressCount == 0);
                }).orElse(false);
    }
}
