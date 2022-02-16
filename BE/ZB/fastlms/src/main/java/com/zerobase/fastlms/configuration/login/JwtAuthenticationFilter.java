package com.zerobase.fastlms.configuration.login;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.fastlms.configuration.token.AuthConstants;
import com.zerobase.fastlms.configuration.token.TokenUtils;
import com.zerobase.fastlms.member.exception.MemberStopUserException;
import com.zerobase.fastlms.member.model.CustomUserDetails;
import com.zerobase.fastlms.member.model.LoginInput;
import com.zerobase.fastlms.member.model.MemberInput;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{ 
	private boolean postOnly = true; 
	
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) { 
		super.setAuthenticationManager(authenticationManager); 
	}
	/* * 해당 필터에서 인증 프로세스 이전에 요청에서 사용자 정보를 가져와서 * Authentication 객체를 인증 프로세스 객체에게 전달하는 역할 */ 
	
	@Override 
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) 
			throws AuthenticationException { 
		System.out.println("AUTH FILTER : 로그인 시도");
		if(postOnly && !request.getMethod().equals("POST")) { 
			throw new AuthenticationServiceException( "Authentication method not supported: " + request.getMethod());
		}
		System.out.println("POST");
		UsernamePasswordAuthenticationToken authRequest = null;
		try {
			LoginInput member = new LoginInput();
			System.out.println(request.getContentType());
			// application/x-www-form-urlencoded
			if(request.getContentType().equals("application/x-www-form-urlencoded")) {
				System.out.println("x-www");
				member.setUserId(obtainUsername(request)); 
				member.setUserPassword(obtainPassword(request));
			} 
			else if(request.getContentType().equals("application/json")) {
				System.out.println("json");
				ObjectMapper objectMapper = new ObjectMapper();
				member = objectMapper.readValue(request.getInputStream(), LoginInput.class);	
			}
			System.out.println(member);
			//if(member.getUserId() == null) { member.setUserId("");} 
			//if(member.getUserPassword() == null) { member.setUserPassword("");}
			if(member.getUserId() == null) { member.setUserId("");} 
			if(member.getUserPassword() == null) { member.setUserPassword("");}

			//토큰 생성
			//authRequest = new UsernamePasswordAuthenticationToken(member.getUserId(), member.getUserPassword()); 
			authRequest = new UsernamePasswordAuthenticationToken(member.getUserId(), member.getUserPassword()); 
		} catch (Exception e) {
			throw new MemberStopUserException("토큰 오류" + e);
		} 
		setDetails(request, authRequest); 
		System.out.println("Details : "+authRequest.getDetails());
		System.out.println("Authori : "+authRequest.getAuthorities());
		System.out.println("Credent : "+authRequest.getCredentials());
		System.out.println("Names   : "+authRequest.getName());
		System.out.println("princip : "+authRequest.getPrincipal());
		System.out.println(authRequest);
		System.out.println("Manager호출");
		Authentication authentication = getAuthenticationManager().authenticate(authRequest);
		System.out.println("newToken성공 : Manager 끝");
		System.out.println("====================================");
		System.out.println("authentication : "+authentication);
		CustomUserDetails detail = (CustomUserDetails)authentication.getPrincipal();
		System.out.println("Principal : " +detail.getMember());
		System.out.println("Principal : " +detail.getAuthorities());
		System.out.println("비번(보이면X): "  +authentication.getCredentials());
		System.out.println("세부사항     : " +authentication.getDetails());
		System.out.println("이름        : " +authentication.getName());
		System.out.println("권한        : " +authentication.getAuthorities());
		System.out.println("===================================="); 
		System.out.println(authentication);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		System.out.println("인증 후 인증 객체 저장 : " + SecurityContextHolder.getContext());
		
		 // session영역에 저장 = return
		 // 리턴 = 권한 관리 security 대신 
		 // JWT사용 중 Session사용 필요 없지만 security의 권한 처리를 위해 사용  
		 
		return authentication;
	} 
/*
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		System.out.println("successfulAuthentication");
		System.out.println("==============SUCCESS=============");
		System.out.println("=============JWT토큰 생성=========");
		System.out.println(FilterChainProxy.class);
		System.out.println(ProviderManager.class);
		System.out.println(AbstractAuthenticationProcessingFilter.class);
		System.out.println("success:"+ authResult);
		
		CustomUserDetails user = (CustomUserDetails)authResult.getPrincipal();
		String accessToken = TokenUtils.generateAccessToken(user);
		String refreshToken = TokenUtils.generateRefreshToken(user);
		System.out.println("chain 전" +request.getRequestURI());
		response.addHeader(AuthConstants.AUTH_HEADER, AuthConstants.TOKEN_TYPE + " " + accessToken);
		System.out.println(SecurityContextHolder.getContext().getAuthentication());
		System.out.println(AuthConstants.AUTH_HEADER+" : "+AuthConstants.TOKEN_TYPE + " " + accessToken);
		System.out.println("==전==");
		
		//super.successfulAuthentication(request, response, chain, authResult);
	}	
*/
}

