package net.thumbtack.school.hospital.validators.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = net.thumbtack.school.hospital.validators.InsertScheduleValidator.class)
public @interface InsertScheduleValid {
    String message() default "WeekSchedule or weekDaysSchedule must be null or empty";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

