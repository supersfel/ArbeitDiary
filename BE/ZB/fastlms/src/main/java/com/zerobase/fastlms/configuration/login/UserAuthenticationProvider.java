package com.zerobase.fastlms.configuration.login;


import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.zerobase.fastlms.admin.dto.MemberLoginDto;
import com.zerobase.fastlms.configuration.token.AuthConstants;
import com.zerobase.fastlms.configuration.token.TokenUtils;
import com.zerobase.fastlms.member.exception.MemberNotEmailAllthException;
import com.zerobase.fastlms.member.model.CustomUserDetails;
import com.zerobase.fastlms.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserAuthenticationProvider implements AuthenticationProvider{
	private final MemberService memberService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
 
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException{
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
		System.out.println("============MANAGER호출 ->PROVIDER============");
		System.out.println(token);
		
		String userId = token.getName();
		String userPw = (String) token.getCredentials();
		
		System.out.println("=========== 입력 받은 값 & 로그인 전 ==========");
		System.out.println("아이디 : " + userId);
		System.out.println("비번  : " + userPw);
		
		CustomUserDetails userDetails = (CustomUserDetails)memberService.loadUserByUsername(userId);
		
		System.out.println("=========== 회원 인증 성공 후 ==========");
		System.out.println(userDetails);
		System.out.println("ID : "+ userDetails.getUsername());
		System.out.println("pw : "+ userDetails.getPassword());
		System.out.println("AU : "+userDetails.getAuthorities());
		if(!bCryptPasswordEncoder.matches(userPw, userDetails.getPassword())) {
			System.out.println("오류");
			throw new BadCredentialsException("Invaild password");
		}
		UsernamePasswordAuthenticationToken newToken = new UsernamePasswordAuthenticationToken(userDetails, userPw, userDetails.getAuthorities());
		System.out.println("Token 생성 완료 : "+ newToken);
		return newToken;

	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
