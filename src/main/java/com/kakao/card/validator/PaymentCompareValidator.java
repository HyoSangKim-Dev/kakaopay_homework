package com.kakao.card.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.kakao.card.data.PaymentVo;

public class PaymentCompareValidator implements ConstraintValidator<PaymentCompare, PaymentVo> {
    
    @Override
    public void initialize(PaymentCompare constraintAnnotation) {
    }

    @Override
    public boolean isValid(PaymentVo param, ConstraintValidatorContext context) {
    	if(param.getPrice() != null && param.getVat() != null) {
    		if(param.getPrice() > param.getVat()) {
    			return true;	// 금액이 더 큼
    		} else {
    			return false;	// vat가 더 큼
    		}
        } else {
            return true;	// vat 가 null, 공백 => 자동 계산
        }
    }
}