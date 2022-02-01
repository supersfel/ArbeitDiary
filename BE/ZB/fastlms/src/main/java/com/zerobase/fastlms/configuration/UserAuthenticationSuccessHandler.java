package com.zerobase.fastlms.configuration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import com.zerobase.fastlms.admin.dto.MemberLoginDto;
import com.zerobase.fastlms.configuration.token.AuthConstants;
import com.zerobase.fastlms.configuration.token.TokenUtils;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.model.CustomUserDetails;
import com.zerobase.fastlms.member.model.MemberInput;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class UserAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{
 	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
		System.out.println("==============SUCCESS=============");
		System.out.println("=============JWT토큰 생성=========");
		System.out.println("success:"+ authentication);
		CustomUserDetails user = (CustomUserDetails)authentication.getPrincipal();
		String accessToken = TokenUtils.generateAccessToken(user);
		String refreshToken = TokenUtils.generateRefreshToken(user);

		response.addHeader(AuthConstants.AUTH_HEADER, AuthConstants.TOKEN_TYPE + " " + accessToken);

		System.out.println(AuthConstants.AUTH_HEADER+" : "+AuthConstants.TOKEN_TYPE + " " + accessToken);
		System.out.println("TOKEN 완료 : "+ authentication);
		System.out.println(request.getSession().getAttribute(AuthConstants.AUTH_HEADER));

		//super.onAuthenticationSuccess(request, response, authentication);				
	}
 }
