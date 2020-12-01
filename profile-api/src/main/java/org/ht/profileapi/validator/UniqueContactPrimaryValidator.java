package org.ht.profileapi.validator;

import org.ht.profileapi.dto.request.internal.AddressContactRequest;
import org.ht.profileapi.dto.request.internal.HierarchyContactRequest;
import org.ht.profileapi.validator.constraint.UniquePrimaryContact;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UniqueContactPrimaryValidator implements ConstraintValidator<UniquePrimaryContact, List> {
    @Override
    public boolean isValid(List hierarchyContactRequests, ConstraintValidatorContext constraintValidatorContext) {
        List values = (List) Optional.ofNullable(hierarchyContactRequests)
                .map(list -> list.stream().filter(contact -> {
                    if (contact instanceof HierarchyContactRequest) {
                        return ((HierarchyContactRequest) contact).isPrimary();
                    }
                    if (contact instanceof AddressContactRequest) {
                        return ((AddressContactRequest) contact).isPrimary();
                    }
                    return false;
                }).collect(Collectors.toList()))
                .orElse(new ArrayList<>());
        return values.size() == 1;
    }
}
