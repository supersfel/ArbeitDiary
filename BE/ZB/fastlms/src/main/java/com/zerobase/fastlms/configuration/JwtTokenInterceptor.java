package com.zerobase.fastlms.configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.zerobase.fastlms.configuration.token.AuthConstants;
import com.zerobase.fastlms.configuration.token.TokenUtils;

public class JwtTokenInterceptor implements HandlerInterceptor{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception{
		String header = request.getHeader(AuthConstants.AUTH_HEADER);
		
		if(header != null) {
			String token = TokenUtils.getTokenFromHeader(header);
			if(TokenUtils.isAccessVaildToken(token)) {
				return true;
			}
		}
		response.sendRedirect("/error/unauthorized");
		return false;
	}
}
