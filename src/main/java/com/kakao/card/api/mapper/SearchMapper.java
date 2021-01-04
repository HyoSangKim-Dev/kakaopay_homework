package com.kakao.card.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kakao.card.data.SearchVo;
import com.kakao.card.data.model.Search;

@Mapper
public interface SearchMapper {

	/**
	 * 결제/취소 내역을 단건으로 조회
	 * 
	 * @param param
	 * @return
	 */
	public Search select(SearchVo param);
	
	/**
	 * 원거래 관리번호로 결제/취소 내역을 목록으로 조회
	 * 
	 * @param param
	 * @return
	 */
	public List<Search> selectList(SearchVo param);

}

