package org.ht.profileapi.validator;

import org.ht.profileapi.dto.request.internal.AddressContactRequest;
import org.ht.profileapi.dto.request.internal.HierarchyContactRequest;
import org.ht.profileapi.validator.constraint.UniquePrimaryContact;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Optional;

public class UniqueContactPrimaryValidator implements ConstraintValidator<UniquePrimaryContact, List> {
    @Override
    public boolean isValid(List hierarchyContactRequests, ConstraintValidatorContext constraintValidatorContext) {
        long count = Optional.ofNullable(hierarchyContactRequests)
                .map(list -> list.stream().filter(contact -> {
                    if (contact instanceof HierarchyContactRequest) {
                        return ((HierarchyContactRequest) contact).isPrimary();
                    }
                    if (contact instanceof AddressContactRequest) {
                        return ((AddressContactRequest) contact).isPrimary();
                    }
                    return false;
                }).count())
                .orElse(-1L);
        return count == -1 || count == 1;
    }
}
