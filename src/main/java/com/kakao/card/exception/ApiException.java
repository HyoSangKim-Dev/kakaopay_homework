package com.kakao.card.exception;

import com.kakao.card.common.ApiEnum;

import lombok.Getter;

/**
 * validation 참고 : https://giyatto.tistory.com/71
 * 
 * Exception 참고 : https://javafactory.tistory.com/1561
 * 
 * @author gytkd
 *
 */
@Getter
public class ApiException {
	
	private Integer errorCode;
	private String errorDesc;
	
	public ApiException(ApiEnum apiEnum) {
		this.errorCode = apiEnum.getErrorCode();
		this.errorDesc = apiEnum.getErrorDesc();
	}
	
}