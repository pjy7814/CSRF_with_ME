package com.ssafy.vue.exception;

public class CryptException extends Exception{
	public CryptException() {
		super("암호화 로직 실패!");
	}
}
