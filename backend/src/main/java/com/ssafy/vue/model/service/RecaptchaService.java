package com.ssafy.vue.model.service;

public interface RecaptchaService {
	public boolean verifyRecaptcha(String recaptchaToken) throws Exception;
}
