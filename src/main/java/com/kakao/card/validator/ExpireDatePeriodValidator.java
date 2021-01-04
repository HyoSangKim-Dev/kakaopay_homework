package com.kakao.card.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExpireDatePeriodValidator implements ConstraintValidator<ExpireDatePeriod, String> {
    private String format;

    @Override
    public void initialize(ExpireDatePeriod constraintAnnotation) {
        this.format = constraintAnnotation.format();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null && !"".equals(value)) {
            try {
            	Date now = new Date();
    			
    			SimpleDateFormat sdf = new SimpleDateFormat(format);
    			sdf.setLenient(false);

    			Date ca = sdf.parse(value);

				// 카드유효기간이 오늘날짜보다 같거나 크면 정상
    			if(ca.getTime() >= now.getTime()) {
    				return true;
    			}
            	
                return false;
            } catch (ParseException e) {
                return false;
            }
        } else {
            return false;
        }
    }
}