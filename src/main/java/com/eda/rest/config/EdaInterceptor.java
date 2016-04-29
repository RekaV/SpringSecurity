package com.eda.rest.config;

import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class EdaInterceptor implements HandlerInterceptor {

	String DEFAULT_USER = "user";
	String DEFAULT_PASS = "user";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		boolean result = false;
		String authHeader = getHeaderValue();
		System.out.println("Pre-handle");
		String header = request.getHeader("Authorization");
		System.out.println("HEAD::" + header);
		if (header.equals(authHeader)) {
			System.out.println("HEADER::::" + header);
			result = true;
		} else {
			System.out.println("UNAUTHORIZED ACCESS");
		}
		return result;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		System.out.println("Post-handle");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

		System.out.println("After Completion");
	}

	public String getHeaderValue() {

		String auth = DEFAULT_USER + ":" + DEFAULT_PASS;
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("ISO-8859-1")));
		String authHeader = "Basic " + new String(encodedAuth);
		System.out.println(authHeader);
		return authHeader;
	}
}
