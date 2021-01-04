package com.kakao.card.data;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.kakao.card.validator.ExpireDateFormat;
import com.kakao.card.validator.ExpireDatePeriod;
import com.kakao.card.validator.PaymentCompare;

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
@PaymentCompare(message = "부가가치세는 결제금액보다 작게 입력해주세요.")
public class PaymentVo {
	@ApiParam(value = "카드번호", required = true)
	@NotNull(message = "카드번호를 입력해주세요.")
    @Pattern(regexp = "[0-9]+", message = "카드번호는 숫자로 입력해주세요.")
    @Size(min = 10, max = 16, message = "카드번호는 {min}~{max}자리로 입력해주세요.")
	private String cardNo;

	@ApiParam(value = "유효기간", required = true)
	@NotNull(message = "유효기간을 입력해주세요.")
	@ExpireDateFormat(format = "MMyy", message = "카드의 유효기간은 MMyy로 입력바랍니다.")
	@ExpireDatePeriod(format = "MMyy", message = "유효기간이 지난 카드는 사용이 불가능 합니다.")
    private String expiryDate;

	@ApiParam(value = "cvc", required = true)
	@NotNull(message = "cvc번호를 입력해주세요.")
    @Pattern(regexp = "[0-9]+", message = "cvc번호는 숫자로 입력해주세요.")
    @Size(min = 3, max = 3, message = "cvc번호를 {min}자리로 입력해주세요.")
    private String cvc;

	@ApiParam(value = "할부개월", required = true)
	@NotNull(message = "할부개월를 입력해주세요.")
    @Min(value = 0, message = "할부개월은 0~12로 입력해주세요.")
    @Max(value = 12, message = "할부개월은 0~12로 입력해주세요.")
	private Integer monthIns;

	@ApiParam(value = "결제금액", required = true)
	@NotNull(message = "결제 금액을 입력해주세요.")
    @Min(value = 100, message = "결제 금액은 {value}원 이상으로 입력이 가능합니다.")
    @Max(value = 1000000000, message = "결제 금액은 {value}원 이하로 입력이 가능합니다.") // 10억원 = 1000000000 = 1,000,000,000
	private Long price;

	@ApiParam(value = "부가가치세", required = false)
    private Long vat;
	
	public Long getVat() {
		if(price != null && vat == null) {
			BigDecimal bd = new BigDecimal(this.price);
			
			return bd.divide(BigDecimal.valueOf(11), RoundingMode.HALF_UP).longValue();
		}
		
		return vat;
	}
}