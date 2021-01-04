package com.kakao.card;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;

import com.kakao.card.common.TestCommon;
import com.kakao.card.data.CancelVo;
import com.kakao.card.data.PaymentVo;

public class MultiThreadTest extends TestCommon {
	
	/**
	 * 출처 : https://trending.tistory.com/10
	 *      https://m.blog.naver.com/hehe5959/221003299814
	 */
	
    /**
     * 동시 결제 테스트
     * 
     * @throws Throwable
     */
    @Test
    public void payment_approved() throws Throwable {
    	Runnable completableFuture1 = new ApprovedThread();
    	Runnable completableFuture2 = new ApprovedThread();
    	Runnable completableFuture3 = new ApprovedThread();
    	 
    	CompletableFuture.allOf(CompletableFuture.runAsync(completableFuture1)
			    			  , CompletableFuture.runAsync(completableFuture2)
			    			  , CompletableFuture.runAsync(completableFuture3)).join();
    }
    
    class ApprovedThread implements Runnable {
    	public void run() {
    		try {
    			// 결제 ==> 11000(1000)원
				approved(new PaymentVo("123456789012345", "0122", "123", 0, 11000L, 1000L));
			} catch (Throwable e) {
				e.printStackTrace();
			}
    	}
    }

    /**
     * 동시 취소 테스트
     * 
     * @throws Throwable
     */
    @Test
    public void payment_cancel() throws Throwable {
    	// 결제 ==> 11000(1000)원
    	String tradeId = approved(new PaymentVo("123456789012345", "0122", "123", 0, 11000L, 1000L));
    	
    	Runnable completableFuture1 = new CancelThread(tradeId);
    	Runnable completableFuture2 = new CancelThread(tradeId);
    	Runnable completableFuture3 = new CancelThread(tradeId);
    	 
    	CompletableFuture.allOf(CompletableFuture.runAsync(completableFuture1)
			    			  , CompletableFuture.runAsync(completableFuture2)
			    			  , CompletableFuture.runAsync(completableFuture3)).join();
    	
    }
    
    class CancelThread implements Runnable {
    	String tradeId;
    	
    	public CancelThread(String tradeId) {
    		this.tradeId = tradeId;
    	}
    	
    	public void run() {
    		try {
    			// 결제취소 ==> 11000(1000)원
    	    	cancel(new CancelVo(tradeId, 11000L, 1000L));
			} catch (Throwable e) {
				e.printStackTrace();
			}
    	}
    }
    
}