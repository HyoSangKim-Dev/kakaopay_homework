package com.kakao.card.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kakao.card.api.service.PaymentService;
import com.kakao.card.common.ApiEnum;
import com.kakao.card.data.CancelVo;
import com.kakao.card.data.PaymentVo;
import com.kakao.card.exception.ParameterApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"1. 결제/취소"}) // MemberController를 대표하는 타이틀 영역에 표시될 정보
@RestController
@RequestMapping(value="/api/payment")
public class PaymentController {
	
	@Autowired
	private PaymentService service;
	
	@Value("${jasypt.key}")
	private String key;
	
	/**
	 * 결제
	 */
	@ApiOperation(value="결제승인", notes="결제 후 카드사 전송 정보를 리턴합니다.")
	@PostMapping("/approved")
	@ResponseBody
	public Object approved(@ModelAttribute @Valid PaymentVo searchVo, BindingResult result) {
		
		if (result.hasErrors()) {
            return new ParameterApiException(ApiEnum.PARAMETER_ERROR, result);
        }
		
		return service.approved(searchVo, key);
	}
	
	/**
	 * 취소
	 */
	@ApiOperation(value="결제취소", notes="결제취소 후 카드사 전송 정보를 리턴합니다.")
	@PostMapping("/cancel")
	@ResponseBody
	public Object cancel(@ModelAttribute @Valid CancelVo searchVo, BindingResult result) {
		
		if (result.hasErrors()) {
            return new ParameterApiException(ApiEnum.PARAMETER_ERROR, result);
        }
		
		return service.cancel(searchVo, key);
	}
}