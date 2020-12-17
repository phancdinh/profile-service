package org.ht.id.profileapi.validator.constraint;

import org.ht.id.profileapi.validator.ContactInfoRequiredValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ContactInfoRequiredValidator.class)
public @interface ContactInfoRequired {
    String message() default "Please input at least 1 primary contact";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
