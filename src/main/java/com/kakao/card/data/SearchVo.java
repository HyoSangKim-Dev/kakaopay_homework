package com.kakao.card.data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
public class SearchVo {
	@ApiParam(value = "관리번호", required = true)
	@NotNull(message = "관리번호를 입력해주세요.")
    @Size(min = 20, max = 20, message = "관리번호를 {min}자리로 입력해주세요.")
	private String tradeId;
}
