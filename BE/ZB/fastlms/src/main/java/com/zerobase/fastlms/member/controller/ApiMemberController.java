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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zerobase.fastlms.admin.dto.MemberLoginDto;
import com.zerobase.fastlms.configuration.UserAuthenticationSuccessHandler;
import com.zerobase.fastlms.configuration.login.JwtAuthenticationFilter;
import com.zerobase.fastlms.configuration.login.UserAuthenticationProvider;
import com.zerobase.fastlms.configuration.token.AuthConstants;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.model.CustomUserDetails;
import com.zerobase.fastlms.member.model.LoginInput;
import com.zerobase.fastlms.member.model.MemberInput;
import com.zerobase.fastlms.member.model.ResetPasswordInput;
import com.zerobase.fastlms.member.repository.MemberRepository;
import com.zerobase.fastlms.member.service.MemberService;
import com.zerobase.fastlms.member.service.impl.MemberServiceImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ApiMemberController {
	private final MemberService memberService;	
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final UserAuthenticationSuccessHandler userAuthenticationSuccessHandler;
	@ResponseBody
	@PostMapping("/api/userRegist")
	public ResponseEntity<?>  userRegist (@RequestBody MemberInput memberInput) {
		// ????????? ?????? ?????????????????? IP????????? ???????????????.
		System.out.println(memberInput);
		boolean result = memberService.register(memberInput);
		System.out.println("MEMBER:" +memberInput);
		return ResponseEntity.ok().body(true);
	}
	
	@PostMapping("/api/login")
	public ResponseEntity<?> login (HttpServletRequest request, HttpServletResponse response, LoginInput loginInput) throws ServletException, IOException {
		System.out.println("==========================================================");
		System.out.println("?????? ??????");
		System.out.println(request.getParameterNames());
		Authentication auth = jwtAuthenticationFilter.attemptAuthentication(request, response);
		System.out.println("????????? ?????? ??????");
		MemberLoginDto token = memberService.getloginToken((CustomUserDetails)auth.getPrincipal());
		boolean result = true;
		if(token == null) {
			result = false;
		}
		
		System.out.println("[toekn] :" +token);
		System.out.println("[result] :" + true);
		response.addHeader(AuthConstants.AUTH_HEADER, AuthConstants.TOKEN_TYPE + " " + token.getAccessToken());
		response.addHeader(AuthConstants.RERESH_HEADER, AuthConstants.TOKEN_TYPE + " " + token.getRefreshToken());
		return ResponseEntity.ok().body(result);
	}
	
	@PostMapping("/api/emailAuth") // get??? requestbody ??????
	public ResponseEntity<?> emailAuth(@RequestBody LoginInput loginInput){
		System.out.println("[????????? ??????]");
		System.out.println(loginInput);
		boolean result = memberService.emailAuth(loginInput.getId());
		return ResponseEntity.ok().body(result);
	}
	
	
	@PostMapping("/api/find/password") // get??? requestbody ??????
	public ResponseEntity<?> findPassword(@RequestBody MemberInput memberInput){
		System.out.println("[API ???????????? ??????]");
		System.out.println(memberInput);
		boolean result = false;
		try {
			result = memberService.sendResetPassword(memberInput.getUserId(), memberInput.getUserName());
		} catch(Exception e) {
			System.out.println(e);
		}
		System.out.println(result);
		return ResponseEntity.ok().body(result);
	}
	
	@PostMapping("/api/reset/password") // get??? requestbody ??????
	public ResponseEntity<?> resetPassword(@RequestBody MemberInput memberInput, HttpServletRequest request){
		System.out.println("[API ???????????? ??????]");
		System.out.println(memberInput);
		String uuid = request.getParameter("id");
		boolean result = false;
		try {
			result  =memberService.checkResetPasswordKey(uuid);
		}catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("[API RESULT] : "+result);
		if(result) {
			result = memberService.resetPassword(uuid, memberInput.getUserPassword());
		}
		System.out.println("[API RESULT] : "+result);
		return ResponseEntity.ok().body(result);
	}
	
	@PostMapping("/api/find/userid") // get??? requestbody ??????
	public ResponseEntity<?> findId(@RequestBody MemberInput memberInput, HttpServletRequest request){
		System.out.println("[API ???????????? ??????]");
		System.out.println(memberInput);
		List<String> userIdList = memberService.getUserId(memberInput.getUserPhone(), memberInput.getUserName());
		
		return ResponseEntity.ok().body(userIdList);
	}
}
