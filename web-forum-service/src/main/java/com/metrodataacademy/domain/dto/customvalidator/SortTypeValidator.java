package com.metrodataacademy.domain.dto.customvalidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

class SortTypeValidator implements ConstraintValidator<ValidSortType, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context){
        if (value.equalsIgnoreCase("A") ||
        value.equalsIgnoreCase("D") ||
        value.equalsIgnoreCase("")) {
            return true;
        }
        return false;
    }
}
