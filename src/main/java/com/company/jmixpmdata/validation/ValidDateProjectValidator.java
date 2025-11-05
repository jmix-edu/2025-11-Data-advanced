package com.company.jmixpmdata.validation;

import com.company.jmixpmdata.entity.Project;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class ValidDateProjectValidator implements ConstraintValidator<ValidDateProject, Project> {
    @Override
    public boolean isValid(Project project, ConstraintValidatorContext context) {
        if (project == null) {
            return false;
        }

        LocalDateTime start = project.getStartDate();
        LocalDateTime end = project.getEndDate();

        if (start == null || end == null) {
            return true;
        }

        return end.isAfter(start);
    }
}
