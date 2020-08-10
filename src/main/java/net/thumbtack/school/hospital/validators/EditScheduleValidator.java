package net.thumbtack.school.hospital.validators;

import net.thumbtack.school.hospital.request.EditScheduleDoctorDtoRequest;
import net.thumbtack.school.hospital.validators.annotations.EditScheduleValid;
import net.thumbtack.school.hospital.validators.annotations.InsertScheduleValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EditScheduleValidator implements ConstraintValidator<EditScheduleValid, EditScheduleDoctorDtoRequest> {

    @Override
    public boolean isValid(EditScheduleDoctorDtoRequest request, ConstraintValidatorContext constraintValidatorContext) {
        return request.getWeekSchedule() == null || request.getWeekDaysSchedule().size() == 0;
    }
}

