package com.kakao.card;

import org.junit.jupiter.api.Test;

import com.kakao.card.common.TestCommon;
import com.kakao.card.data.CancelVo;
import com.kakao.card.data.PaymentVo;

public class PaymentTest extends TestCommon {
	
    /**
     * 결제 테스트 (정상 결제)
     * 
     * @throws Throwable
     */
    @Test
    public void payment_approved_success() throws Throwable {
    	// 결제 ==> 11000(1000)원
    	approved(new PaymentVo("123456789012345", "0122", "123", 0, 11000L, 1000L));
    }
    
    /**
     * 결제 테스트 (결제 실패 :: 파라메터 오류)
     * 
     * @throws Throwable
     */
    @Test
    public void payment_approved_fail() throws Throwable {
    	// 결제 ==> 10(100)원
    	approved(new PaymentVo("123456789", "1220", "12A", 13, 10L, 100L));
    }
    
    /**
     * 취소 테스트 (정상 결제취소)
     * 
     * @throws Throwable
     */
    @Test
    public void payment_cancel_success() throws Throwable {
    	// 결제 ==> 11000(1000)원
    	String tradeId = approved(new PaymentVo("123456789012345", "0122", "123", 0, 11000L, 1000L));
    	
    	// 결제취소 ==> 11000(1000)원
    	cancel(new CancelVo(tradeId, 11000L, 1000L));
    	
    }
    
    /**
     * 취소 테스트 (결제취소 실패)
     *   - 파라메터 오류
     *   - 없는 결제 번호
     * 
     * @throws Throwable
     */
    @Test
    public void payment_cancel_fail() throws Throwable {
    	// 결제취소 ==> 10(100)원
    	cancel(new CancelVo("mSHq7RFnrv9mCSYkn1yz", 10L, 100L));
    	
    	// 결제취소 ==> 11000(1000)원
    	cancel(new CancelVo("AAAAAAAAAAAAAAAAAAAA", 11000L, 1000L));
    	
    }
    
}