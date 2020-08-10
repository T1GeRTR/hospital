package net.thumbtack.school.hospital.validators.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = net.thumbtack.school.hospital.validators.MaxNameLengthValidator.class)
public @interface MaxNameLength {
    String message() default "Wrong name length";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

