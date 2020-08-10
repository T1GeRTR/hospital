package net.thumbtack.school.hospital.validators.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = net.thumbtack.school.hospital.validators.EditScheduleValidator.class)
public @interface EditScheduleValid {
    String message() default "WeekSchedule or weekDaysSchedule must be null or empty";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

