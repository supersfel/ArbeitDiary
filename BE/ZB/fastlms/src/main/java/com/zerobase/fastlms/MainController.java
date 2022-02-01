package com.zerobase.fastlms;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MainController {
	@GetMapping("/")
	public String index(Authentication auth, Principal principal, Authentication authentication) {
		System.out.println("메인");
		System.out.println(auth);
		return "index";
	}
	
	@GetMapping("/error/denied")
	public String error() {
		return "/error/denied";
	}
}
