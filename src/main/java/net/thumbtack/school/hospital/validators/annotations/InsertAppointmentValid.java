package net.thumbtack.school.hospital.validators.annotations;

import net.thumbtack.school.hospital.validators.InsertAppointmentValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = InsertAppointmentValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface InsertAppointmentValid {
    String message() default "Speciality or doctorId must be null or empty";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

