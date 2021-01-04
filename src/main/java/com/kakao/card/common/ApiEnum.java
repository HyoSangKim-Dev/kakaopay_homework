package com.kakao.card.common;

import lombok.Getter;

@Getter
public enum ApiEnum {
	/**
	 * 첨고 : https://woowabros.github.io/tools/2017/07/10/java-enum-uses.html
	 */
	// 공통
	SUCCESS(0, "정상"),
	FAIL(1, "오류가 발생 하였습니다."),
    
	// 파라메터
	PARAMETER_ERROR(20, "파라메터 오류가 발생하였습니다."),
    
	// 조회
	NOT_FOUND_TRADE_ID(30, "존재하지 않는 관리번호 입니다."),
    
	// 결제/취소 진행중
	LOCK_APPROVED(40, "결제가 진행중인 카드정보 입니다."),
	LOCK_CANCEL(41, "결제취소가 진행중인 거래번호 입니다."),
    
    // 결제취소
    CANCEL_CARD(50, "결제취소가 진행중인 카드정보 입니다."),
    CANCEL_PRICE(51, "입력하신 금액으로는 취소가 불가능 합니다."),
    CANCEL_VAT(52, "취소가능한 부가가치세 금액이 부족합니다."),
    CANCEL_MORE_VAT(53, "결제금액 취소 후 부가가치세가 남아있어 취소가 불가능합니다.");
	
    private final int errorCode;
    private final String errorDesc;

    ApiEnum(int errorCode, String errorDesc) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }
}
