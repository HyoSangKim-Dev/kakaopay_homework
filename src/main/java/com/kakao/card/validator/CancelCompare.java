package com.kakao.card.validator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = CancelCompareValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface CancelCompare {
    String message();

    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};

}