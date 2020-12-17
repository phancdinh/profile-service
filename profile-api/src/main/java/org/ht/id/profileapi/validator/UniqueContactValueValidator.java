package org.ht.id.profileapi.validator;

import lombok.extern.slf4j.Slf4j;
import org.ht.id.profileapi.dto.request.internal.AddressContactRequest;
import org.ht.id.profileapi.dto.request.internal.HierarchyContactRequest;
import org.ht.id.profileapi.validator.constraint.UniqueContactValue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
public class UniqueContactValueValidator implements ConstraintValidator<UniqueContactValue, List> {
    @Override
    public boolean isValid(List hierarchyContactRequests, ConstraintValidatorContext constraintValidatorContext) {
        return Optional.ofNullable(hierarchyContactRequests)
                .map(list -> {
                    Set<String> uniqueList = new HashSet<>(list.size());
                    list.forEach(contact -> {
                        if (contact instanceof HierarchyContactRequest) {
                            uniqueList.add(((HierarchyContactRequest) contact).getValue());
                        }
                        if (contact instanceof AddressContactRequest) {
                            uniqueList.add(((AddressContactRequest) contact).getFullAddress());
                        }
                    });
                    return uniqueList.size() == list.size();
                }).orElse(true);
    }
}
