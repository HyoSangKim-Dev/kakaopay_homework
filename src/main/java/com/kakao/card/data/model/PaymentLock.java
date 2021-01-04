package com.kakao.card.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentLock {
	private String payGb;
	private String privateKey;
}
