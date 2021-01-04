package com.kakao.card.common;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

public class Utils {
	
	public static void main(String[] args) {
		System.out.println(getFormat("3", 10, "RS"));
		System.out.println(getFormat("3", 10, "RZ"));
		System.out.println(getFormat("3", 10, "LS"));
		System.out.println(getFormat("HOMEWORK", 10, "LS"));
		
		System.out.println(maskCardNo("1234567890"));
		System.out.println(maskCardNo("1234567890123456"));
		
		
	}
	
	/**
	 * 거래번호를 생성
	 * @return
	 */
	public static String getId() {
		return RandomStringUtils.random(20,"ABCDEFHIJKLMNOPSRQTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");
	}
	
	public static String getFormat(Object data, int length, String type) {
		if("RS".contentEquals(type)) {
			return StringUtils.leftPad(String.valueOf(data), length, " ");
		} else if("RZ".contentEquals(type)) {
			return StringUtils.leftPad(String.valueOf(data), length, "0");
		} else if("LS".contentEquals(type)) {
			return StringUtils.rightPad(String.valueOf(data), length, " ");
		}
		return String.valueOf(data);
    }
	
	public static String maskCardNo(String str) {
		int mid = str.length() - 9;
        
        String prefix = str.substring(0, 6);
        String endfix = str.substring(str.length() - 3);
        
        return prefix + StringUtils.repeat("*", mid) + endfix;
	}
}
