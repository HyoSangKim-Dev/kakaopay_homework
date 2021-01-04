package com.kakao.card.data.model;

import org.apache.ibatis.type.Alias;

import com.kakao.card.data.CancelVo;
import com.kakao.card.data.PaymentVo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Alias("paymentInfo")
public class PaymentInfo {
	private String tradeId;
	private String payGb;
	private String cardNo;
	private String expiryDate;
	private String cvc;
	private String cardInfo;
	private String monthIns;
	private Long price;
	private Long vat;
	private String orgTradeId;
	
	private String sendData;
	private String status;
	
	public PaymentInfo(PaymentVo vo) {
		this.payGb = "PAYMENT";
		this.cardNo = vo.getCardNo();
		this.expiryDate = vo.getExpiryDate();
		this.cvc = vo.getCvc();
		this.monthIns = String.valueOf(vo.getMonthIns());
		this.price = vo.getPrice();
		this.vat = vo.getVat();
		this.orgTradeId = "";
	}
	
	public PaymentInfo(SearchInfo vo, CancelVo param) {
		this.payGb = "CANCEL";
		this.cardNo = vo.getCardNo();
		this.expiryDate = vo.getExpiryDate();
		this.cvc = vo.getCvc();
		this.monthIns = "00";
		this.price = param.getPrice();
		this.vat = param.getVat();
		this.orgTradeId = vo.getTradeId();
	}
	
}