package com.kakao.card.data.model;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Alias("searchInfo")
public class SearchInfo {
	private String tradeId;
	private String payGb;
	private String cardNo;
	private String expiryDate;
	private String cvc;
	private String monthIns;
	private Long price;
	private Long vat;
	private String orgTradeId;
	private String regDt;

	private String status;
	
	public SearchInfo(Search vo) {
		this.tradeId = vo.getTradeId();
		this.payGb = vo.getPayGb();
		this.monthIns = vo.getMonthIns();
		this.price = vo.getPrice();
		this.vat = vo.getVat();
		this.orgTradeId = vo.getOrgTradeId();
		this.regDt = vo.getRegDt();

		this.status = vo.getStatus();
	}
}
