package com.kakao.card.api.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kakao.card.api.mapper.PaymentMapper;
import com.kakao.card.common.ApiEnum;
import com.kakao.card.common.Jasypt;
import com.kakao.card.common.Utils;
import com.kakao.card.data.CancelVo;
import com.kakao.card.data.PaymentVo;
import com.kakao.card.data.SearchVo;
import com.kakao.card.data.model.Payment;
import com.kakao.card.data.model.PaymentInfo;
import com.kakao.card.data.model.PaymentLock;
import com.kakao.card.data.model.RemainderInfo;
import com.kakao.card.data.model.SearchInfo;
import com.kakao.card.exception.ApiException;

@Service
public class PaymentService {

	@Autowired
	private PaymentMapper mapper;
	
	@Autowired
	private SearchService searchService;
	
	@Autowired
	private CardService cardService;
		
	@Autowired
	private Jasypt jasypt;

	/**
	 * 결제 승인
	 * 
	 * @param param
	 * @param key
	 * @return
	 */
	public Object approved(PaymentVo param, String key) {

		// 파라메터 검증 완료
		// 여기까지 오면 정상적인 데이터
		
		// 동시제어
		PaymentLock lock = new PaymentLock("PAYMENT", param.getCardNo());
		if(!isLock(lock)) {
			return new ApiException(ApiEnum.LOCK_APPROVED);
		}

		String cardInfo = param.getCardNo() + "|" + param.getExpiryDate() + "|" + param.getCvc();
		String encCardInfo = jasypt.enc(key, cardInfo);
		
        try {
			String tradeId = Utils.getId();
			
			PaymentInfo info = new PaymentInfo(param);
			info.setTradeId(tradeId);
			info.setCardInfo(encCardInfo);
			mapper.insert(info);
			
			String sendData = cardService.send(info);
			
			return new Payment(tradeId, sendData);
		
        } catch (Exception e) {
        	e.printStackTrace();
        	return new ApiException(ApiEnum.FAIL);
        } finally {
        	mapper.deleteLock(lock);
        }
	}
	
	/**
	 * 결제 취소
	 * 
	 * @param param
	 * @param key
	 * @return
	 */
	public Object cancel(CancelVo param, String key) {

		// 파라메터 검증 완료
		// 여기까지 오면 정상적인 데이터
		
		// 동시제어
		PaymentLock lock = new PaymentLock("CANCEL", param.getTradeId());
		if(!isLock(lock)) {
			return new ApiException(ApiEnum.LOCK_CANCEL);
		}
		
		try {
		
			SearchVo search = new SearchVo();
			search.setTradeId(param.getTradeId());
			SearchInfo payment = searchService.select(search, key, false);
			
			if(payment == null) {
				return new ApiException(ApiEnum.NOT_FOUND_TRADE_ID);
			}
			
			// 취소 가능한 금액/부가가치세가 있는지 확인
			RemainderInfo remainder = mapper.remainder(search);
			// 취소 가능한 금액 확인
			if(param.getPrice() > remainder.getRemainderPrice()) {
	            return new ApiException(ApiEnum.CANCEL_PRICE);
	        }
			// 취소 가능한 부가가치세 확인
			Long currentVat = param.getVat();
			if(currentVat == null) {
				// 부가가치세 입력 안함
				// 부가가치세를 입력 안한경우 자동 계산된 부가세 보다 남아있는 부가세가 적으면 남아있는 부가세를 사용한다.
				currentVat = Math.min(getAutoCalcVat(param.getPrice()), remainder.getRemainderVat());
				param.setVat(currentVat);
			}
	        if(currentVat > remainder.getRemainderVat()) {
	        	return new ApiException(ApiEnum.CANCEL_VAT);
	        }
	        // 결제 금액 전부가 취소 되었는데 부가가치세가 남아있는지 확인
	        if(remainder.getRemainderPrice() - param.getPrice() == 0
	        && remainder.getRemainderVat() - currentVat > 0) {
	        	return new ApiException(ApiEnum.CANCEL_MORE_VAT);
	        }
			
			String cardInfo = payment.getCardNo() + "|" + payment.getExpiryDate() + "|" + payment.getCvc();
			String encCardInfo = jasypt.enc(key, cardInfo);

			String tradeId = Utils.getId();
			
			PaymentInfo info = new PaymentInfo(payment, param);
			info.setTradeId(tradeId);
			info.setCardInfo(encCardInfo);
			mapper.insert(info);
			
			String sendData = cardService.send(info);
			
			return new Payment(tradeId, sendData);
		
        } catch (Exception e) {
        	return new ApiException(ApiEnum.FAIL);
        } finally {
        	mapper.deleteLock(lock);
        }
	}

	public Long getAutoCalcVat(Long price) {
		BigDecimal bd = new BigDecimal(price);
		
		return bd.divide(BigDecimal.valueOf(11), RoundingMode.HALF_UP).longValue();
	}
	
	/**
	 * 테이블을 이용한 Multi Thread 환경 구성
	 *   - 메서드에 synchronized를 사용하여 처리함
	 * 
	 * @param vo
	 * @return
	 */
	public synchronized boolean isLock(PaymentLock vo) {
		return mapper.mergeLock(vo) > 0 ? true : false;
	}
}