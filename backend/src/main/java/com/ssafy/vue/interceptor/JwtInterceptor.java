package com.ssafy.vue.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ssafy.vue.exception.UnAuthorizedException;
import com.ssafy.vue.model.service.JwtService;

@Component
public class JwtInterceptor implements HandlerInterceptor {

	private static final String HEADER_AUTH = "auth-token";

	@Autowired
	private JwtService jwtService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		final String token = request.getHeader(HEADER_AUTH);

		if (token != null && jwtService.checkToken(token)) {
			return true;
		} else {
			throw new UnAuthorizedException();
		}

	}

}
