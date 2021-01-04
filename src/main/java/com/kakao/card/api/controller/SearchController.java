package com.kakao.card.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kakao.card.api.service.SearchService;
import com.kakao.card.common.ApiEnum;
import com.kakao.card.data.SearchVo;
import com.kakao.card.exception.ParameterApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"2. 조회"})
@RestController
@RequestMapping(value="/api/payment")
public class SearchController {
	
	@Autowired
	private SearchService service;
	
	@Value("${jasypt.key}")
	private String key;
	
	/**
	 * 조회
	 */
	@ApiOperation(value="조회", notes="관리번호의 결제 승인/취소 정보를 단건으로 조회합니다.")
	@GetMapping("/search")
	public Object search(@ModelAttribute SearchVo searchVo, BindingResult result) {
		
		if(result.hasErrors()) {
			return new ParameterApiException(ApiEnum.PARAMETER_ERROR, result);
		}
		
		return service.select(searchVo, key, true);
	}
	
	/**
	 * 조회(목록)
	 */
	@ApiOperation(value="거래이력 조회", notes="관리번호의 결제 승인/취소 정보를 목록으로 조회합니다.")
	@GetMapping("/searchList")
	public Object searchList(@ModelAttribute SearchVo searchVo, BindingResult result) {
		
		if(result.hasErrors()) {
			return new ParameterApiException(ApiEnum.PARAMETER_ERROR, result);
		}
		
		return service.selectList(searchVo, key, true);
	}
}