package com.kakao.card.data.model;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Alias("payment") 
public class Payment {
	private String tradeId;
	private String sendData;
}