package net.thumbtack.school.hospital.validators.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = net.thumbtack.school.hospital.validators.NotDuplicatesValidator.class)
public @interface NotDuplicates {
    String message() default "DoctorIds are duplicates";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

