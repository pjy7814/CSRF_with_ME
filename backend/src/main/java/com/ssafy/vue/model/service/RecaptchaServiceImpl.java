package com.ssafy.vue.model.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.ssafy.util.MyException;
import com.ssafy.vue.config.RecaptchaConfig;
import com.ssafy.vue.exception.RecaptchaException;

@Service
@PropertySource("classpath:config.properties")
public class RecaptchaServiceImpl implements RecaptchaService{
	@Value("${recaptcha-secret-key}")
	private String secretKey;
	
	public boolean verifyRecaptcha(String recaptchaToken) throws MyException{
		try {
			RecaptchaConfig.setSecretKey(secretKey);
			return RecaptchaConfig.verify(recaptchaToken);	
		}catch(RecaptchaException e) {
			throw new MyException("캡챠 인증 오류");
		}
	}
}
