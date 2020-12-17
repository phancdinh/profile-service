package org.ht.id.profileapi.validator.constraint;

import org.ht.id.profileapi.validator.UniqueContactValueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueContactValueValidator.class)
public @interface UniqueContactValue {
    String message() default "Value is duplicated.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
