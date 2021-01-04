package com.kakao.card.data.model;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("remainderInfo")
public class RemainderInfo {
	private Long remainderPrice;
	private Long RemainderVat;
}
