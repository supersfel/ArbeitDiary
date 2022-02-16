package com.zerobase.fastlms.member.controller;

import java.security.Principal;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.ibatis.annotations.Select;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.dto.MemberLoginDto;
import com.zerobase.fastlms.configuration.login.JwtAuthenticationFilter;
import com.zerobase.fastlms.configuration.token.AuthConstants;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.member.model.CustomUserDetails;
import com.zerobase.fastlms.member.model.LoginInput;
import com.zerobase.fastlms.member.model.MemberInput;
import com.zerobase.fastlms.member.model.ResetPasswordInput;
import com.zerobase.fastlms.member.model.SelectAutoSelf;
import com.zerobase.fastlms.member.repository.MemberRepository;
import com.zerobase.fastlms.member.service.MemberService;
import com.zerobase.fastlms.member.service.WorkService;

@Controller
public class MemberController {
	private final MemberService memberService;
	private final WorkService workService;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	
	public MemberController(MemberService memberService, JwtAuthenticationFilter jwtAuthenticationFilter, WorkService workService) {
		this.memberService = memberService;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.workService = workService;
	}
	
	@GetMapping("/member/register")
	public String register(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("회원가입GET");
		return "member/register";
	}
	
	@PostMapping("/member/register")
	public String registerSubmit(Model model, MemberInput parameter ,Principal principal) {
		System.out.println("회원가입POST");
		System.out.println(parameter.toString());
		
		boolean result = memberService.register(parameter);
		model.addAttribute("result", result);
		System.out.println("pricd :" +principal);
		return "member/register-complete";
	}
	
	@GetMapping("/member/email-auth")
	public String emailAuth(Model model, HttpServletRequest request, Principal principal) {
		String uuid = request.getParameter("id");
		System.out.println(uuid);
		System.out.println("pricd :" +principal);
		boolean result = memberService.emailAuth(uuid);
		
		model.addAttribute("result", result);
		return "member/email-auth";
	}
	
	@GetMapping("/member/info")
	public String memberInfo(Model model, Principal principal) {
		System.out.println("정보");
		Principal prin = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("[AUTH] : "+prin);
		System.out.println("[AUTH] : "+principal);
		MemberDto detail = memberService.detail(principal.getName());
		
		model.addAttribute("detail", detail);
		return "member/info";
	}
	
	
	@PostMapping("/member/login")
	public String memberLoginSubmit(HttpServletRequest request, HttpServletResponse response, LoginInput loginInput) {
		System.out.println("Post로그인");
		System.out.println("==========================================================");
		System.out.println("유저 인증");
		System.out.println(loginInput);

		return "member/login";
	}
	
	@GetMapping("/member/login")
	public String memberLogin(HttpServletRequest request) {
		System.out.println("Get로그인");
		return "member/login";
	}
	
	@GetMapping("/member/find/password")
	public String findPassword() {
		System.out.println("비밀번호 찾기");
		return "member/find-password";
	}
	
	@PostMapping("/member/find/password")
	public String findPasswordSubmit(ResetPasswordInput parameter, Model model) {
		boolean result = false;
		try {
			result = memberService.sendResetPassword(parameter.getUserId(), parameter.getUserName());
		} catch(Exception e) {
			System.out.println(e);
		}
		
		System.out.println(parameter);
		model.addAttribute("result", result);	
		return "member/find-password-result";
	}
	
	@GetMapping("/member/reset/password")
	public String resetPassword(Model model, HttpServletRequest request,ResetPasswordInput parameter) {
		System.out.println("리셋 패스워드");
		System.out.println(parameter);
		
		String uuid = request.getParameter("id");
		boolean result = memberService.checkResetPasswordKey(uuid);
		
		model.addAttribute("result", result);
		return "member/reset-password";
	}
	
	@PostMapping("/member/reset/password")
	public String resetPaswwordSubmit(Model model, ResetPasswordInput parameter) {
		System.out.println("reset post:"+parameter);
		boolean result = false;
		try {
			result = memberService.resetPassword(parameter.getId(), parameter.getPassword());
		}catch(Exception e) {
			System.out.println("Hello");
		}
		System.out.println(result);
		model.addAttribute("result", result);
		return "member/reset-password-result";
	}
	
	@GetMapping("/member/find/email")
	public String findEmail() {
		System.out.println("아이디 찾기");
		return "member/find-email";
	}
	
	@GetMapping("/member/password")
	public String memberPassword() {
		System.out.println("비밀번호 변경");
		return "member/password";
	}
	
	@PostMapping("/member/password")
	public String submitMemberPassword(Model model, MemberInput memberInput, Principal principal) {
		System.out.println("비밀번호 변경");
		String userId = principal.getName();
		System.out.println(memberInput);
		memberInput.setUserId(userId);
		ServiceResult result = memberService.updateMemberPassword(memberInput);
		if(!result.isResult()) {
			model.addAttribute("message", result.getMessage());
			return "member/password";
		}
	
		return "redirect:/member/info";
	}
	
	@GetMapping("/member/takecourse")
	public String memberTakeCourse() {
		System.out.println("수강 정보");
		return "member/takecourse";
	}

	@GetMapping("/setwork")
	public String work() {
		return "member/work";
	}
	
	@PostMapping("/setwork")
	public String submitWork(MemberInput memberInput, Principal principal, Long projectId) {
		projectId = 4L;
		boolean result = workService.testAdd(memberInput.getDay(), principal.getName(), projectId, SelectAutoSelf.SELF.toString());
		
		return "member/work";
	}
}
