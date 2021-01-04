package com.kakao.card.data.model;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data 
@Alias("search") 
public class Search {
	private String tradeId;
	private String payGb;
	private String cardInfo;
	private String monthIns;
	private Long price;
	private Long vat;
	private String orgTradeId;
	private String regDt;
	
	private String status;
}