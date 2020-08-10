package net.thumbtack.school.hospital.validators;

import net.thumbtack.school.hospital.validators.annotations.InsertAppointmentValid;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class InsertAppointmentValidator implements ConstraintValidator<InsertAppointmentValid, Object> {

    public void initialize(InsertAppointmentValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object request, ConstraintValidatorContext constraintValidatorContext) {
        String speciality = (String) new BeanWrapperImpl(request)
                .getPropertyValue("speciality");
        Integer doctorId = (Integer) new BeanWrapperImpl(request)
                .getPropertyValue("doctorId");
        return speciality == null || doctorId == null || doctorId == 0;
    }
}

