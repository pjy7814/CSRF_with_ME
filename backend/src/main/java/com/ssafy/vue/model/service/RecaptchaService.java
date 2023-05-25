package com.ssafy.vue.model.service;

import com.ssafy.util.MyException;

public interface RecaptchaService {
	public boolean verifyRecaptcha(String recaptchaToken) throws MyException;
}
