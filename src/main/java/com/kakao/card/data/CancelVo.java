package com.kakao.card.data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.kakao.card.validator.CancelCompare;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@CancelCompare(message = "부가가치세는 결제금액보다 작게 입력해주세요.")
public class CancelVo {
	@ApiParam(value = "관리번호", required = true)
	@NotNull(message = "관리번호를 입력해주세요.")
    @Size(min = 20, max = 20, message = "관리번호를 {min}자리로 입력해주세요.")
	private String tradeId;

	@ApiParam(value = "취소금액", required = true)
	@NotNull(message = "결제취소 금액을 입력해주세요.")
    @Min(value = 100, message = "결제취소 금액은 {value}원 이상으로 입력이 가능합니다.")
    @Max(value = 1000000000, message = "결제취소 금액은 {value}원 이하로 입력이 가능합니다.") // 10억원 = 1000000000 = 1,000,000,000
	private Long price;

	@ApiParam(value = "부가가치세", required = false)
    private Long vat;
	
    public CancelVo(String tradeId, Long price) {
    	this.tradeId = tradeId;
    	this.price = price;
    }
}