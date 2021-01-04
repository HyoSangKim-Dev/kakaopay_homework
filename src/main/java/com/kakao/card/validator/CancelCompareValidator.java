package com.kakao.card.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.kakao.card.data.CancelVo;

public class CancelCompareValidator implements ConstraintValidator<CancelCompare, CancelVo> {
    
    @Override
    public void initialize(CancelCompare constraintAnnotation) {
    }

    @Override
    public boolean isValid(CancelVo param, ConstraintValidatorContext context) {
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