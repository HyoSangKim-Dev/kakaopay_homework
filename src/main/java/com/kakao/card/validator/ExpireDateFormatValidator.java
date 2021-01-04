package com.kakao.card.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExpireDateFormatValidator implements ConstraintValidator<ExpireDateFormat, String> {
    private String format;

    @Override
    public void initialize(ExpireDateFormat constraintAnnotation) {
        this.format = constraintAnnotation.format();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null && !"".equals(value)) {
            try {
            	SimpleDateFormat sdf = new SimpleDateFormat(format);
            	sdf.setLenient(false); // 1520이 들어올경우 자동으로 21년3월로 계산되는것을 방지
            	sdf.parse(value);
            	
                return true;
            } catch (ParseException e) {
                return false;
            }
        } else {
            return false;
        }
    }
}