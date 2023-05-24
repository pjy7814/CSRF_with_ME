package com.ssafy.vue.model.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.ssafy.vue.config.RecaptchaConfig;

@Service
@PropertySource("classpath:config.properties")
public class RecaptchaServiceImpl implements RecaptchaService{
	@Value("${recaptcha-secret-key}")
	private String secretKey;
	
	public boolean verifyRecaptcha(String recaptchaToken) throws Exception{
		RecaptchaConfig.setSecretKey(secretKey);
		return RecaptchaConfig.verify(recaptchaToken);	
	}
}
