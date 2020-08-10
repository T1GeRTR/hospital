package net.thumbtack.school.hospital.validators;

import net.thumbtack.school.hospital.validators.annotations.MaxNameLength;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MaxNameLengthValidator implements ConstraintValidator<MaxNameLength, String> {
    @Value("${max_name_length}")
    private int max_name_length;
    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return name.length() <= max_name_length;
    }
}

