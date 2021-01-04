package com.kakao.card.validator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = PaymentCompareValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface PaymentCompare {
    String message();

    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};

}