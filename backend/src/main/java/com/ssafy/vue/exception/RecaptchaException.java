package com.ssafy.vue.exception;

public class RecaptchaException extends Exception {
	public RecaptchaException() {
		super("캡챠 인증 에러");
	}
}
