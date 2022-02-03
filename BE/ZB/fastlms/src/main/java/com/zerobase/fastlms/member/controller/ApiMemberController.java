package com.zerobase.fastlms.member.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zerobase.fastlms.admin.dto.MemberLoginDto;
import com.zerobase.fastlms.configuration.UserAuthenticationSuccessHandler;
import com.zerobase.fastlms.configuration.login.JwtAuthenticationFilter;
import com.zerobase.fastlms.configuration.login.UserAuthenticationProvider;
import com.zerobase.fastlms.configuration.token.AuthConstants;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.model.CustomUserDetails;
import com.zerobase.fastlms.member.model.LoginInput;
import com.zerobase.fastlms.member.model.MemberInput;
import com.zerobase.fastlms.member.model.ResetPasswordInput;
import com.zerobase.fastlms.member.repository.MemberRepository;
import com.zerobase.fastlms.member.service.MemberService;
import com.zerobase.fastlms.member.service.impl.MemberServiceImpl;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class ApiMemberController {
	private final MemberService memberService;	
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final UserAuthenticationSuccessHandler userAuthenticationSuccessHandler;
	@ResponseBody
	@PostMapping("/api/userRegist")
	public ResponseEntity<?>  userRegist (Model model,@RequestBody MemberInput memberInput) {
		// 요청을 보낸 클라이언트의 IP주소를 반환합니다.
		System.out.println(memberInput);
		boolean result = memberService.register(memberInput);
		System.out.println("MEMBER:" +memberInput);
		return ResponseEntity.ok().body(result);
	}
	
	@PostMapping("/api/login")
	public ResponseEntity<?> login (HttpServletRequest request, HttpServletResponse response, LoginInput loginInput) throws ServletException, IOException {
		System.out.println("==========================================================");
		System.out.println("유저 인증");
		Authentication auth = jwtAuthenticationFilter.attemptAuthentication(request, response);
		System.out.println("엑세스 토큰 생성");
		MemberLoginDto token = memberService.getloginToken((CustomUserDetails)auth.getPrincipal());
		System.out.println(token);
		response.addHeader(AuthConstants.AUTH_HEADER, AuthConstants.TOKEN_TYPE + " " + token.getAccessToken());
		response.addHeader(AuthConstants.RERESH_HEADER, AuthConstants.TOKEN_TYPE + " " + token.getRefreshToken());
		return ResponseEntity.ok().body(auth.getPrincipal());
	}
	

}
