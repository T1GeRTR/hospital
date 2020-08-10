package net.thumbtack.school.hospital.validators;

import net.thumbtack.school.hospital.validators.annotations.NotDuplicates;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NotDuplicatesValidator implements ConstraintValidator<NotDuplicates, List<Integer>> {
    @Override
    public boolean isValid(List<Integer> doctorIds, ConstraintValidatorContext constraintValidatorContext) {
        return new HashSet<>(doctorIds).size() == doctorIds.size();
    }
}

