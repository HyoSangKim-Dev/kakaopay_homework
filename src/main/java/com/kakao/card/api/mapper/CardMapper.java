package com.kakao.card.api.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.kakao.card.data.model.PaymentInfo;

@Mapper
public interface CardMapper {

	/**
	 * 카드사 전송 내역 저장
	 * 
	 * @param param
	 * @return
	 */
	public int insert(PaymentInfo param);

}

