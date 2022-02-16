package com.zerobase.fastlms;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.zerobase.fastlms.configuration.token.AuthConstants;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MainController {
	@GetMapping("/")
	public String index(HttpServletRequest request, HttpServletResponse response, Principal principal) {
		System.out.println("메인");
		System.out.println(principal);
		return "index";
	}
	
	@GetMapping("/error/denied")
	public String error() {
		return "/error/denied";
	}
}
