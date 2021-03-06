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
	/* * ?????? ???????????? ?????? ???????????? ????????? ???????????? ????????? ????????? ???????????? * Authentication ????????? ?????? ???????????? ???????????? ???????????? ?????? */ 
	
	@Override 
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) 
			throws AuthenticationException { 
		System.out.println("AUTH FILTER : ????????? ??????");
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

			//?????? ??????
			//authRequest = new UsernamePasswordAuthenticationToken(member.getUserId(), member.getUserPassword()); 
			authRequest = new UsernamePasswordAuthenticationToken(member.getUserId(), member.getUserPassword()); 
		} catch (Exception e) {
			throw new MemberStopUserException("?????? ??????" + e);
		} 
		setDetails(request, authRequest); 
		System.out.println("Details : "+authRequest.getDetails());
		System.out.println("Authori : "+authRequest.getAuthorities());
		System.out.println("Credent : "+authRequest.getCredentials());
		System.out.println("Names   : "+authRequest.getName());
		System.out.println("princip : "+authRequest.getPrincipal());
		System.out.println(authRequest);
		System.out.println("Manager??????");
		Authentication authentication = getAuthenticationManager().authenticate(authRequest);
		System.out.println("newToken?????? : Manager ???");
		System.out.println("====================================");
		System.out.println("authentication : "+authentication);
		CustomUserDetails detail = (CustomUserDetails)authentication.getPrincipal();
		System.out.println("Principal : " +detail.getMember());
		System.out.println("Principal : " +detail.getAuthorities());
		System.out.println("??????(?????????X): "  +authentication.getCredentials());
		System.out.println("????????????     : " +authentication.getDetails());
		System.out.println("??????        : " +authentication.getName());
		System.out.println("??????        : " +authentication.getAuthorities());
		System.out.println("===================================="); 
		System.out.println(authentication);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		System.out.println("?????? ??? ?????? ?????? ?????? : " + SecurityContextHolder.getContext());
		
		 // session????????? ?????? = return
		 // ?????? = ?????? ?????? security ?????? 
		 // JWT?????? ??? Session?????? ?????? ????????? security??? ?????? ????????? ?????? ??????  
		 
		return authentication;
	} 
/*
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		System.out.println("successfulAuthentication");
		System.out.println("==============SUCCESS=============");
		System.out.println("=============JWT?????? ??????=========");
		System.out.println(FilterChainProxy.class);
		System.out.println(ProviderManager.class);
		System.out.println(AbstractAuthenticationProcessingFilter.class);
		System.out.println("success:"+ authResult);
		
		CustomUserDetails user = (CustomUserDetails)authResult.getPrincipal();
		String accessToken = TokenUtils.generateAccessToken(user);
		String refreshToken = TokenUtils.generateRefreshToken(user);
		System.out.println("chain ???" +request.getRequestURI());
		response.addHeader(AuthConstants.AUTH_HEADER, AuthConstants.TOKEN_TYPE + " " + accessToken);
		System.out.println(SecurityContextHolder.getContext().getAuthentication());
		System.out.println(AuthConstants.AUTH_HEADER+" : "+AuthConstants.TOKEN_TYPE + " " + accessToken);
		System.out.println("==???==");
		
		//super.successfulAuthentication(request, response, chain, authResult);
	}	
*/
}

