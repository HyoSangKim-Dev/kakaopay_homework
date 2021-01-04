package com.kakao.card.api.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.kakao.card.data.SearchVo;
import com.kakao.card.data.model.PaymentInfo;
import com.kakao.card.data.model.PaymentLock;
import com.kakao.card.data.model.RemainderInfo;

@Mapper
public interface PaymentMapper {

	/**
	 * 결제 내역 저장
	 * 
	 * @param param
	 * @return
	 */
	public int insert(PaymentInfo param);
	
	/**
	 * 원거래 관리번호로 결제, 취소된 금액의 나머지 잔액
	 *   - 결제승인 금액 - 취소금액
	 * 
	 * @param param
	 * @return
	 */
	public RemainderInfo remainder(SearchVo param);
	
	/**
	 * 테이블을 이용한 Multi Thread 환경 구성
	 *   - 락 키값이 없으면 입력
	 * 
	 * @param vo
	 * @return
	 */
	public int mergeLock(PaymentLock vo);
	
	/**
	 * 
	 * 테이블을 이용한 Multi Thread 환경 구성
	 *   - 락 테이블에 입력된 값을 삭제
	 * 
	 * @param vo
	 * @return
	 */
	public int deleteLock(PaymentLock vo);
	
}