package com.kakao.card.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.kakao.card.common.ApiEnum;
import com.kakao.card.data.ExceptionInfo;

import lombok.Getter;

public class ParameterApiException extends ApiException {
	
	@Getter
	private List<ExceptionInfo> errors;
	
	public ParameterApiException(ApiEnum apiEnum, BindingResult result) {
		super(apiEnum);
		this.errors = new ArrayList<>();
		
		for(ObjectError error : result.getAllErrors()){
            this.errors.add(new ExceptionInfo(error.getDefaultMessage()));
        }
	}
	
}