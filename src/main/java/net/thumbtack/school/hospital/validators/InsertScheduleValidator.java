package net.thumbtack.school.hospital.validators;

import net.thumbtack.school.hospital.request.DayScheduleDtoRequest;
import net.thumbtack.school.hospital.request.RegisterDoctorDtoRequest;
import net.thumbtack.school.hospital.request.WeekDaysScheduleDto;
import net.thumbtack.school.hospital.validators.annotations.InsertScheduleValid;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class InsertScheduleValidator implements ConstraintValidator<InsertScheduleValid, RegisterDoctorDtoRequest> {



    @Override
    public boolean isValid(RegisterDoctorDtoRequest request, ConstraintValidatorContext constraintValidatorContext) {
        WeekDaysScheduleDto weekSchedule = (WeekDaysScheduleDto) new BeanWrapperImpl(request)
                .getPropertyValue("weekSchedule");
        ArrayList<DayScheduleDtoRequest> weekDaysSchedule = (ArrayList<DayScheduleDtoRequest>) new BeanWrapperImpl(request)
                .getPropertyValue("weekDaysSchedule");
        return weekSchedule == null || weekDaysSchedule == null;
    }
}

