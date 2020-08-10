package net.thumbtack.school.hospital.validators;

import net.thumbtack.school.hospital.validators.annotations.MinPasswordLength;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MinPasswordLengthValidator implements ConstraintValidator<MinPasswordLength, String> {
    @Value("${min_password_length}")
    private int min_password_length;

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return password.length() >= min_password_length;
    }
}

