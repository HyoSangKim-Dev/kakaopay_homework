package com.kakao.card.common;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.stereotype.Component;

@Component
public class Jasypt {

	/**
	 * 출처 : https://luvstudy.tistory.com/67
	 */
	
	public String enc(String key, String str) {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(key);
		encryptor.setAlgorithm("PBEWithMD5AndDES");
		return encryptor.encrypt(str);
	}

	public String dec(String key, String str) {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(key);
		encryptor.setAlgorithm("PBEWithMD5AndDES");
		return encryptor.decrypt(str);
	}
	
}
