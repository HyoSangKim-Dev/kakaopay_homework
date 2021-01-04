package com.kakao.card;

import org.junit.jupiter.api.Test;

import com.kakao.card.common.TestCommon;
import com.kakao.card.data.CancelVo;
import com.kakao.card.data.PaymentVo;

public class IntegratedCaseTest extends TestCommon {

    /**
     * 선택문제 Test Case 1
     * 
     * @throws Throwable
     */
    @Test
    public void test_case1() throws Throwable {
    	// 결제 [성공] ==> 11,000(1,000)원
    	String tradeId = approved(new PaymentVo("123456789012345", "0122", "123", 0, 11000L, 1000L));
    	
    	// 부분 취소 [성공] ==> 1,100(100)원 > 9,900(900)
    	cancel(new CancelVo(tradeId, 1100L, 100L));
    	
    	// 부분 취소 [성공] ==> 3,300(null)원 > 6,600(600)
    	cancel(new CancelVo(tradeId, 3300L));
    	
    	// 부분 취소 [실패] ==> 7,000(null)원 > 6,600(600)
    	cancel(new CancelVo(tradeId, 7000L));
    	
    	// 부분 취소 [실패] ==> 6,600(700)원 > 6,600(600)
    	cancel(new CancelVo(tradeId, 6600L, 700L));
    	
    	// 부분 취소 [성공] ==> 6,600(600)원 > 0(0)
    	cancel(new CancelVo(tradeId, 6600L, 600L));
    	
    	// 부분 취소 [실패] ==> 100(null)원 > 0(0)
    	cancel(new CancelVo(tradeId, 100L));
    	
    }
    
    /**
     * 선택문제 Test Case 2
     * 
     * @throws Throwable
     */
    @Test
    public void test_case2() throws Throwable {
    	// 결제 [성공] ==> 20,000(909)원
    	String tradeId = approved(new PaymentVo("123456789012345", "0122", "123", 0, 20000L, 909L));
    	
    	// 부분 취소 [성공] ==> 10,000(0)원 > 10,000(909)
    	cancel(new CancelVo(tradeId, 10000L, 0L));
    	
    	// 부분 취소 [실패] ==> 10,000(0)원 > 10,000(909)
    	cancel(new CancelVo(tradeId, 10000L, 0L));
    	
    	// 부분 취소 [성공] ==> 10,000(909)원 > 0(0)
    	cancel(new CancelVo(tradeId, 10000L, 909L));
    	
    }
    
    /**
     * 선택문제 Test Case 3
     * 
     * @throws Throwable
     */
    @Test
    public void test_case3() throws Throwable {
    	// 결제 [성공] ==> 20,000(null)원 > 20,000(1818)
    	String tradeId = approved(new PaymentVo("123456789012345", "0122", "123", 0, 20000L, 909L));
    	
    	// 부분 취소 [성공] ==> 10,000(1,000)원 > 10,000(818)
    	cancel(new CancelVo(tradeId, 10000L, 0L));
    	
    	// 부분 취소 [실패] ==> 10,000(909)원 > 10,000(818)
    	cancel(new CancelVo(tradeId, 10000L, 0L));
    	
    	// 부분 취소 [성공] ==> 10,000(null)원 > 0(0)
    	cancel(new CancelVo(tradeId, 10000L, 909L));
    	
    }
    
}