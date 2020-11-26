package org.ht.profile.validator;

import org.ht.profile.dto.request.internal.AddressContactRequest;
import org.ht.profile.dto.request.internal.HierarchyContactRequest;
import org.ht.profile.validator.constraint.UniquePrimaryContact;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UniqueContactPrimaryValidator implements ConstraintValidator<UniquePrimaryContact, List> {
    @Override
    public boolean isValid(List hierarchyContactRequests, ConstraintValidatorContext constraintValidatorContext) {
        if (hierarchyContactRequests == null) return true;
         int count = Optional.of(hierarchyContactRequests).filter(contact -> {
            if (contact instanceof HierarchyContactRequest) {
                return ((HierarchyContactRequest) contact).isPrimary();
            }
            if (contact instanceof AddressContactRequest) {
                return ((AddressContactRequest) contact).isPrimary();
            }
            return false;
        }).orElse(new ArrayList()).size();
        return count <= 1;
    }
}
