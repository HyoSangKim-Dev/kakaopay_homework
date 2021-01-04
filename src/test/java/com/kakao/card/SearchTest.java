package com.kakao.card;

import org.junit.jupiter.api.Test;

import com.kakao.card.common.TestCommon;
import com.kakao.card.data.CancelVo;
import com.kakao.card.data.PaymentVo;
import com.kakao.card.data.SearchVo;

public class SearchTest extends TestCommon {
	
    /**
     * 조회 테스트
     * @throws Throwable
     */
    @Test
    public void payment_search() throws Throwable {
    	// 결제 ==> 11000(1000)원
    	String approvedTradeId = approved(new PaymentVo("123456789012345", "0122", "123", 0, 11000L, 1000L));
    	
    	// 결제 내역 조회
    	search(new SearchVo(approvedTradeId));
    	
    	// 결제취소 ==> 11000(1000)원
    	String cancelTradeId = cancel(new CancelVo(approvedTradeId, 11000L, 1000L));
    	
    	// 결제취소 내역 조회
    	search(new SearchVo(cancelTradeId));
    	
    }
    
    
}