package org.ht.profile.validator.constraint;

import org.ht.profile.validator.UniqueContactPrimaryValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueContactPrimaryValidator.class)
public @interface UniquePrimaryContact {
    String message() default "Please input only 1 primary field value.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
