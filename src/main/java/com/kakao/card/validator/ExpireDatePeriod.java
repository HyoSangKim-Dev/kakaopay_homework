package com.kakao.card.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 
 * 카드의 유효기간이 지났는지 체크
 * 
 * @author gytkd
 *
 */

@Constraint(validatedBy = ExpireDatePeriodValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExpireDatePeriod {
	String format();
    String message();

    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};

}
