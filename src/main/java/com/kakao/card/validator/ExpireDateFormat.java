package com.kakao.card.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 
 * 카드의 유효기간을 체크
 * 
 * 유효기간은 앞 2자리는 월, 뒤 2자리는 년도의 마지막 2자리를 사용
 * 
 * 출처 : https://www.latera.kr/blog/2019-07-23-test-custom-validation/
 *      https://jongmin92.github.io/2019/11/18/Spring/bean-validation-1/
 * @author gytkd
 *
 */

@Constraint(validatedBy = ExpireDateFormatValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExpireDateFormat {
	String format();
    String message();

    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};

}
