package com.ssafy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class OpenCrypt {

	public static byte[] generateKey(String algorithm, int keySize) throws NoSuchAlgorithmException {		// keySize에 따라 보안 강도가 달라짐

		KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm); // algorithm을 외부에서 만들어서 넣으면 거기에 맞는 key를 생성함
		keyGenerator.init(keySize);
		SecretKey key = keyGenerator.generateKey();
		return key.getEncoded();		//byte배열 형태
	}

	public static String aesEncrypt(String msg, byte[] key) throws Exception { // 암호화
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES"); // AES 알고리즘을 활용하겠습니다
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		String iv = "AAAAAAAAAAAAAAAA";
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(iv.getBytes())); // ENCRYPT_MODE: 암호화 모드,
																						// skeySpec: 키
		byte[] encrypted = cipher.doFinal(msg.getBytes()); // 암호화 진행

		return byteArrayToHex(encrypted);
	}

	public static String aesDecrypt(String msg, byte[] key) throws Exception { // 복호화
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		String iv = "AAAAAAAAAAAAAAAA";
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(iv.getBytes()));
		byte[] encrypted = hexToByteArray(msg); // 16진수 -> byteArray로 바꾸기
		byte[] original = cipher.doFinal(encrypted);
		return new String(original);
	}

	public static byte[] hexToByteArray(String hex) { // 16진수 -> byteArray로 바꾸기
		if (hex == null || hex.length() == 0) {
			return null;
		}

		byte[] ba = new byte[hex.length() / 2];
		for (int i = 0; i < ba.length; i++) {
			ba[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return ba;
	}

	public static String getSHA256(String source, String salt) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(source.getBytes());
		md.update(salt.getBytes());
		byte[] byteData = md.digest();
		String hashMsg = byteArrayToHex(byteData);
//		System.out.println(hashMsg);
		return hashMsg;
	}

	// byte[] to hex
	public static String byteArrayToHex(byte[] ba) {
		if (ba == null || ba.length == 0) {
			return null;
		}

		StringBuffer sb = new StringBuffer(ba.length * 2);
		String hexNumber;
		for (int x = 0; x < ba.length; x++) {
			hexNumber = "0" + Integer.toHexString(0xff & ba[x]);

			sb.append(hexNumber.substring(hexNumber.length() - 2));
		}
		return sb.toString();
	}

}
