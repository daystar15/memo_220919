package com.memo.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtils {
	// static 붙어있으면 메인 메모리에 올라와야 수행할 수 있음
	public static String md5(String message) {
		String encData = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");

			byte[] bytes = message.getBytes();
	        md.update(bytes);
	        byte[] digest = md.digest();

	        for(int i = 0; i < digest.length; i++ ) {
	            encData += Integer.toHexString(digest[i]&0xff); // 16진수로 변환하는 과정
	        }
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return encData;
	}
}